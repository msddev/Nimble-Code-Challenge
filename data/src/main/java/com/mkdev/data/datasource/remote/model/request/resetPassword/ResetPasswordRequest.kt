package com.mkdev.data.datasource.remote.model.request.resetPassword

import com.google.gson.annotations.SerializedName

data class ResetPasswordRequest(
    @SerializedName("user")
    val user: UserRequest,
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("client_secret")
    val clientSecret: String,
)