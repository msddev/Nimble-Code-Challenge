package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.request.singIn.SignInRequest

object SignInRequestFactory {
    fun createSignInRequest(
        grantType: String = "password",
        email: String = "test@example.com",
        password: String = "password",
        clientId: String = "client_id",
        clientSecret: String = "client_secret"
    ) = SignInRequest(grantType, email, password, clientId, clientSecret)
}