package com.mkdev.presentation.model.local

internal data class TextFieldErrorModel(
    val isError: Boolean = false,
    val errorText: Int? = null,
)