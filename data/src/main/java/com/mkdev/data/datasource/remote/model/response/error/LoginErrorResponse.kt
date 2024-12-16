package com.mkdev.data.datasource.remote.model.response.error

internal data class LoginErrorResponse(
    val errors: List<LoginError>
)

internal data class LoginError(
    val detail: String,
    val code: String
)