package com.mkdev.data.datasource.remote.interceptor

/*class AuthInterceptor @Inject constructor(
    private val userLocalSource: UserLocalSource,
    private val apiService: Provider<ApiService>,
) : Interceptor {
    private val mutex = Mutex()

    override fun intercept(chain: Interceptor.Chain): Response {
        val req = chain.request().also { debug("[1] $it") }

        if (ApiConfigs.NO_AUTH in req.headers.values(ApiConfigs.CUSTOM_HEADER)) {
            return chain.proceedWithToken(req, null)
        }

        val token = runBlocking(appDispatchers.io) { userLocalSource.user().first() }
            ?.token
            .also { debug("[2] $req $it") }
        val res = chain.proceedWithToken(req, token)

        if (res.code != HTTP_UNAUTHORIZED || token == null) {
            return res
        }

        debug("[3] $req")

        val newToken: String? = runBlocking(appDispatchers.io) {
            mutex.withLock {
                val user =
                    userLocalSource.user().first().also { debug("[4] $req $it") }
                val maybeUpdatedToken = user?.token

                when {
                    user == null || maybeUpdatedToken == null -> null.also { debug("[5-1] $req") } // already logged out!
                    maybeUpdatedToken != token -> maybeUpdatedToken.also { debug("[5-2] $req") } // refreshed by another request
                    else -> {
                        debug("[5-3] $req")

                        val refreshTokenRes = apiService.get()
                            .refreshToken(user.toRefreshTokenBody())
                            .also { debug("[6] $req $it") }

                        when (refreshTokenRes.code()) {
                            HTTP_OK -> {
                                debug("[7-1] $req")
                                refreshTokenRes.body()!!.token.also { updatedToken ->
                                    userLocalSource.update {
                                        (it ?: return@update null)
                                            .toBuilder()
                                            .setToken(updatedToken)
                                            .build()
                                    }
                                }
                            }

                            HTTP_UNAUTHORIZED -> {
                                debug("[7-2] $req")
                                userLocalSource.update { null }
                                null
                            }

                            else -> {
                                debug("[7-3] $req")
                                null
                            }
                        }
                    }
                }
            }
        }

        return if (newToken !== null) chain.proceedWithToken(req, newToken) else res
    }

    private fun Interceptor.Chain.proceedWithToken(req: Request, token: String?): Response =
        req.newBuilder()
            .apply {
                if (token !== null) {
                    addHeader("Authorization", "Bearer $token")
                }
            }
            .removeHeader(ApiConfigs.CUSTOM_HEADER)
            .build()
            .let(::proceed)

    @Suppress("NOTHING_TO_INLINE")
    private inline fun debug(s: String) = Timber.tag(LOG_TAG).d(s)

    private companion object {
        private val LOG_TAG = AuthInterceptor::class.java.simpleName
    }
}

private fun UserLocal.toRefreshTokenBody() = RefreshTokenBody(refreshToken, username)*/
