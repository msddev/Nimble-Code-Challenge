package com.mkdev.data.datasource.remote.model.request.resetPassword

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    @SerialName("email")
    val email: String
)
