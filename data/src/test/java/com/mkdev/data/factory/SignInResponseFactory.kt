package com.mkdev.data.factory

import com.infinum.jsonapix.core.resources.DefaultError
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponseModel

object SignInResponseFactory {

    fun createSignInResponse(
        accessToken: String = "access_token",
        refreshToken: String = "refresh_token",
        createdAt: Long = 1001001,
        expiresIn: Long = 1000220,
        tokenType: String = "token_type",
    ): SignInResponseModel {
        val data = SignInResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            createdAt = createdAt,
            expiresIn = expiresIn,
            tokenType = tokenType
        )
        return SignInResponseModel(data = data)
    }

    fun createSignInErrorResponse(
        accessToken: String = "access_token",
        refreshToken: String = "refresh_token",
        createdAt: Long = 1001001,
        expiresIn: Long = 1000220,
        tokenType: String = "token_type",
    ): SignInResponseModel {
        val data = SignInResponse(
            accessToken = accessToken,
            refreshToken = refreshToken,
            createdAt = createdAt,
            expiresIn = expiresIn,
            tokenType = tokenType
        )
        return SignInResponseModel(
            data = data,
            errors = listOf(DefaultError(code = "", title = "", detail = "", status = ""))
        )
    }
}