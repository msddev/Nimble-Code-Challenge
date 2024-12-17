package com.mkdev.presentation.screen.authentication.resetPassword

internal sealed class ResetPasswordUiState {
    data object Idle : ResetPasswordUiState()
    data object Loading : ResetPasswordUiState()
    data class Success(val message: String?) : ResetPasswordUiState()
    data class Error(val message: String?) : ResetPasswordUiState()
}