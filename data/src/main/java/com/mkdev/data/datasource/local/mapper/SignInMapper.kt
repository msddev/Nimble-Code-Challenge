package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import javax.inject.Inject

class SignInMapper @Inject constructor() {
    fun mapToUserLocal(signInResponse: SignInResponse?): UserLocal? {
        return signInResponse?.let { attributes ->
            UserLocal.newBuilder()
                .setAccessToken(attributes.accessToken)
                .setRefreshToken(attributes.refreshToken)
                .setCreatedAt(attributes.createdAt.toString())
                .setExpiresIn(attributes.expiresIn.toString())
                .setTokenType(attributes.tokenType)
                .build()
        }
    }
}