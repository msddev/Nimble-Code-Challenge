package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import javax.inject.Inject

class SignInMapper @Inject constructor() {
    fun mapToUserLocal(signInResponse: SignInResponse?): UserLocal? {
        return signInResponse?.attributes?.run {
            UserLocal.newBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setCreatedAt(createdAt)
                .setExpiresIn(expiresIn)
                .setTokenType(tokenType)
                .build()
        }
    }
}