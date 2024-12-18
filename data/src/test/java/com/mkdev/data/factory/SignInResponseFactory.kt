package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.response.singIn.SignInAttributesResponse
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse

object SignInResponseFactory {

    fun createSignInResponse(
        accessToken: String = "access_token",
        refreshToken: String = "refresh_token",
        createdAt: String = "created_at",
        expiresIn: String = "expires_in",
        tokenType: String = "token_type",
        id: String = "id",
        type: String = "type"
    ): SignInResponse {
        val attributes = SignInAttributesResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            createdAt = createdAt,
            expiresIn = expiresIn,
            tokenType = tokenType
        )
        return SignInResponse(attributes = attributes, id = id, type = type)
    }
}