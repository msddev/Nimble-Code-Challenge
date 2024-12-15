package com.mkdev.presentation.screen.authentication.signin

import com.mkdev.presentation.model.signIn.SignInModel

sealed class SignInUiState {
    data object Idle : SignInUiState()
    data object Loading : SignInUiState()
    data class Success(val data: SignInModel?) : SignInUiState()
    data class Error(val message: String?) : SignInUiState()
}