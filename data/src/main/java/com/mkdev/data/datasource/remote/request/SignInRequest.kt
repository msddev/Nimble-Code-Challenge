package com.mkdev.data.datasource.remote.request

data class SignInRequest(
    val grantType: String = "password",
    val email: String,
    val password: String,
)