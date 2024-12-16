package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.remote.model.response.singIn.SignInResponse

internal fun SignInResponse.toUserLocal(): UserLocal = UserLocal.newBuilder()
    .setAccessToken(attributes.accessToken)
    .setRefreshToken(attributes.refreshToken)
    .setCreatedAt(attributes.createdAt)
    .setExpiresIn(attributes.expiresIn)
    .setTokenType(attributes.tokenType)
    .build()