package com.mkdev.data.datasource.remote.interceptor

import android.util.Log
import com.mkdev.data.BuildConfig
import com.mkdev.data.datasource.local.dataStore.UserLocalSource
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.model.request.refreshToken.RefreshTokenRequest
import com.mkdev.data.utils.ApiConfigs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.net.HttpURLConnection.HTTP_OK
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED
import javax.inject.Inject
import javax.inject.Provider


class AuthInterceptor @Inject constructor(
    private val userLocalSource: UserLocalSource,
    private val authApi: Provider<AuthApi>,
) : Interceptor {
    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().also {
            debug("[1] $it")
        }

        if (ApiConfigs.NO_AUTH in request.headers.values(ApiConfigs.CUSTOM_HEADER)) {
            return chain.proceedWithToken(request, null)
        }

        val accessToken = runBlocking(Dispatchers.IO) {
            userLocalSource.user().first()
        }?.accessToken.also {
            debug("[2] $request $it")
        }

        val response = chain.proceedWithToken(request, accessToken)

        if (response.code != HTTP_UNAUTHORIZED || accessToken == null) {
            return response
        }

        debug("[3] $request")

        val newToken: String? = runBlocking(Dispatchers.IO) {
            mutex.withLock {
                val user = userLocalSource.user().first().also { debug("[4] $request $it") }
                val maybeUpdatedToken = user?.accessToken

                when {
                    user == null || maybeUpdatedToken == null -> null.also { debug("[5-1] $request") } // already logged out!
                    maybeUpdatedToken != accessToken -> maybeUpdatedToken.also { debug("[5-2] $request") } // refreshed by another request
                    else -> {
                        debug("[5-3] $request")

                        val refreshTokenResponse =
                            authApi.get().refreshToken(
                                RefreshTokenRequest(
                                    grantType = "password",
                                    refreshToken = user.refreshToken,
                                    clientId = BuildConfig.CLIENT_ID,
                                    clientSecret = BuildConfig.CLIENT_SECRET
                                )
                            )
                                .also { debug("[6] $request $it") }

                        when (refreshTokenResponse.code()) {
                            HTTP_OK -> {
                                debug("[7-1] $request")
                                refreshTokenResponse.body()!!.data?.attributes?.also { data ->
                                    userLocalSource.update {
                                        (it ?: return@update null)
                                            .toBuilder()
                                            .setAccessToken(data.accessToken)
                                            .setRefreshToken(data.refreshToken)
                                            .setCreatedAt(data.createdAt)
                                            .setExpiresIn(data.expiresIn)
                                            .setTokenType(data.tokenType)
                                            .build()
                                    }
                                }?.run {
                                    this.accessToken
                                }
                            }

                            HTTP_UNAUTHORIZED -> {
                                debug("[7-2] $request")
                                userLocalSource.update { null }
                                null
                            }

                            else -> {
                                debug("[7-3] $request")
                                null
                            }
                        }
                    }
                }
            }
        }

        return if (newToken !== null) chain.proceedWithToken(request, newToken) else response
    }

    private fun Interceptor.Chain.proceedWithToken(request: Request, token: String?): Response =
        request.newBuilder()
            .apply {
                if (token !== null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .removeHeader(ApiConfigs.CUSTOM_HEADER)
            .build()
            .let(::proceed)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun debug(s: String) = Log.d(LOG_TAG, s)

    private companion object {
        private val LOG_TAG = AuthInterceptor::class.java.simpleName
    }
}