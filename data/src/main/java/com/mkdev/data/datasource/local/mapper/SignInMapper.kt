package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import javax.inject.Inject

class SignInMapper @Inject constructor() {
    fun mapToUserLocal(signInResponse: SignInResponse?): UserLocal? {
        return signInResponse?.attributes?.let { attributes ->
            UserLocal.newBuilder()
                .setAccessToken(attributes.accessToken)
                .setRefreshToken(attributes.refreshToken)
                .setCreatedAt(attributes.createdAt)
                .setExpiresIn(attributes.expiresIn)
                .setTokenType(attributes.tokenType)
                .build()
        }
    }
}