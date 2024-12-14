package com.mkdev.domain.entity.signIn

data class SignInEntity(
    val accessToken: String,
    val createdAt: Int,
    val expiresIn: Int,
    val refreshToken: String,
    val tokenType: String
)