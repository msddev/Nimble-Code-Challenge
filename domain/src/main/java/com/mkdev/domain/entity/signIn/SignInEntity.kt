package com.mkdev.domain.entity.signIn

data class SignInEntity(
    val accessToken: String,
    val createdAt: String,
    val expiresIn: String,
    val refreshToken: String,
    val tokenType: String
)