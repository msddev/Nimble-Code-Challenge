package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse
import com.mkdev.domain.entity.signIn.SignInEntity

fun SignInResponse.toSignInEntity(): SignInEntity {
    return SignInEntity(
        accessToken = attributes.accessToken,
        createdAt = attributes.createdAt,
        expiresIn = attributes.expiresIn,
        refreshToken = attributes.refreshToken,
        tokenType = attributes.tokenType
    )
}