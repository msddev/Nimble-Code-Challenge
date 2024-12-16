package com.mkdev.data.datasource.remote.model.request.singIn

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
)