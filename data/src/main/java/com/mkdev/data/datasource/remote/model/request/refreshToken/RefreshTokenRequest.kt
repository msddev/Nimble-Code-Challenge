package com.mkdev.data.datasource.remote.model.request.refreshToken

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    @SerialName("grant_type")
    val grantType: String,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("client_id")
    val clientId: String,
    @SerialName("client_secret")
    val clientSecret: String,
)