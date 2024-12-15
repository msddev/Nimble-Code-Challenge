package com.mkdev.presentation.model.signIn

data class SignInModel(
    val accessToken: String,
    val createdAt: String,
    val expiresIn: String,
    val refreshToken: String,
    val tokenType: String
)