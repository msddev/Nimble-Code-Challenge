package com.mkdev.data.factory

import com.mkdev.data.datasource.local.UserLocal

object UserLocalFactory {
    fun createUserLocal(
        accessToken: String = "access_token",
        refreshToken: String = "refresh_token",
        createdAt: String = "created_at",
        expiresIn: String = "expires_in",
        tokenType: String = "token_type"
    ): UserLocal {
        return UserLocal.newBuilder()
            .setAccessToken(accessToken)
            .setRefreshToken(refreshToken)
            .setCreatedAt(createdAt)
            .setExpiresIn(expiresIn)
            .setTokenType(tokenType)
            .build()
    }
}