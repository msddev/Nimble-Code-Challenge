package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdev.domain.usecase.ResetPasswordUseCase
import com.mkdev.domain.utils.Resource
import com.mkdev.presentation.screen.authentication.resetPassword.ResetPasswordUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {

    private val _resetPasswordState =
        MutableStateFlow<ResetPasswordUiState>(ResetPasswordUiState.Idle)
    val resetPasswordState = _resetPasswordState.asStateFlow()

    fun resetPassword(
        email: String,
    ) {
        viewModelScope.launch {
            resetPasswordUseCase(email = email)
                .onEach { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _resetPasswordState.value = ResetPasswordUiState.Loading
                        }

                        is Resource.Success -> {
                            _resetPasswordState.value = ResetPasswordUiState.Success(message = result.data)
                        }

                        is Resource.Error -> {
                            _resetPasswordState.value =
                                ResetPasswordUiState.Error(message = result.message)
                        }
                    }
                }.launchIn(this)
        }
    }
}