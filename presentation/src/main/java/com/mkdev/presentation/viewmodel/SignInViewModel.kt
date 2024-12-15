package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdev.domain.usecase.SignInUseCase
import com.mkdev.domain.utils.Resource
import com.mkdev.presentation.mapper.toSignInModel
import com.mkdev.presentation.screen.authentication.signin.SignInUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val state = _state.asStateFlow()

    fun signIn(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            signInUseCase(
                grantType = "password",
                email = email,
                password = password,
            ).onEach { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = SignInUiState.Loading
                    }

                    is Resource.Success -> {
                        _state.value = SignInUiState.Success(data = result.data?.toSignInModel())
                    }

                    is Resource.Error -> {
                        _state.value = SignInUiState.Error(message = result.message)
                    }
                }
            }.launchIn(this)
        }
    }
}