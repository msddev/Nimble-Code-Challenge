package com.mkdev.data.datasource.remote.model.response.singIn

import com.infinum.jsonapix.annotations.JsonApiX
import com.infinum.jsonapix.core.JsonApiModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("token")
@JsonApiX("token")
data class SignInResponse(
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("expires_in")
    val expiresIn: Long,
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("token_type")
    val tokenType: String
) : JsonApiModel()