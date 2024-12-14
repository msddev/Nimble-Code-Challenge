package com.mkdev.data.datasource.remote.model.request.singIn

data class SignInRequest(
    val grantType: String = "password",
    val email: String,
    val password: String,
)