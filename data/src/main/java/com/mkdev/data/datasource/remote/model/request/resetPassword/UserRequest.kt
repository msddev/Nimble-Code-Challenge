package com.mkdev.data.datasource.remote.model.request.resetPassword

import com.google.gson.annotations.SerializedName

data class UserRequest(
    @SerializedName("email")
    val email: String
)
