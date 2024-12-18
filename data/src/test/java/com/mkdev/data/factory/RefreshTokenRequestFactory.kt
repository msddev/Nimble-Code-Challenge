package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.request.refreshToken.RefreshTokenRequest

object RefreshTokenRequestFactory {
    fun createRefreshTokenRequest(
        grantType: String = "refresh_token",
        refreshToken: String = "refresh_token",
        clientId: String = "client_id",
        clientSecret: String = "client_secret"
    ) = RefreshTokenRequest(grantType, refreshToken, clientId, clientSecret)
}