package com.mkdev.data.factory

import com.mkdev.data.datasource.remote.model.request.resetPassword.ResetPasswordRequest
import com.mkdev.data.datasource.remote.model.request.resetPassword.UserRequest

object ResetPasswordRequestFactory {
    fun createResetPasswordRequest(
        email: String = "test@example.com",
        clientId: String = "client_id",
        clientSecret: String = "client_secret"
    ) = ResetPasswordRequest(UserRequest(email), clientId, clientSecret)
}