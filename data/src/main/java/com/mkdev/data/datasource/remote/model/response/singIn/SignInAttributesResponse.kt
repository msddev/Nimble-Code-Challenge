package com.mkdev.data.datasource.remote.model.response.singIn


import com.google.gson.annotations.SerializedName

data class SignInAttributesResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("expires_in")
    val expiresIn: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("token_type")
    val tokenType: String
)