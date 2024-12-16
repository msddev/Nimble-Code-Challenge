package com.mkdev.data.datasource.remote.interceptor

import com.mkdev.data.BuildConfig
import com.mkdev.data.datasource.local.datastore.UserLocalSource
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
        val request = chain.request()

        if (ApiConfigs.NO_AUTH in request.headers.values(ApiConfigs.CUSTOM_HEADER)) {
            return chain.proceedWithToken(request, null)
        }

        val accessToken = runBlocking(Dispatchers.IO) {
            userLocalSource.user().first()
        }?.accessToken

        val response = chain.proceedWithToken(request, accessToken)

        if (response.code != HTTP_UNAUTHORIZED || accessToken == null) {
            return response
        }

        val newToken: String? = runBlocking(Dispatchers.IO) {
            mutex.withLock {
                val user = userLocalSource.user().first()
                val maybeUpdatedToken = user?.accessToken

                when {
                    user == null || maybeUpdatedToken == null -> null // already logged out!
                    maybeUpdatedToken != accessToken -> maybeUpdatedToken // refreshed by another request
                    else -> {
                        val refreshTokenResponse =
                            authApi.get().refreshToken(
                                RefreshTokenRequest(
                                    grantType = "refresh_token",
                                    refreshToken = user.refreshToken,
                                    clientId = BuildConfig.CLIENT_ID,
                                    clientSecret = BuildConfig.CLIENT_SECRET
                                )
                            )

                        when (refreshTokenResponse.code()) {
                            HTTP_OK -> {
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
                                userLocalSource.update { null }
                                null
                            }

                            else -> {
                                null
                            }
                        }
                    }
                }
            }
        }

        return if (newToken !== null) {
            response.close()
            chain.proceedWithToken(request, newToken)
        } else {
            response
        }
    }

    private fun Interceptor.Chain.proceedWithToken(request: Request, token: String?): Response {
        return request.newBuilder()
            .apply {
                if (token !== null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .removeHeader(ApiConfigs.CUSTOM_HEADER)
            .build()
            .let(::proceed)
    }
}