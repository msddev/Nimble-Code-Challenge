package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdev.domain.usecase.IsUserSignedInUseCase
import com.mkdev.domain.usecase.SignInUseCase
import com.mkdev.domain.utils.Resource
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
    private val isUserSignedInUseCase: IsUserSignedInUseCase
) : ViewModel() {

    private val _signInState = MutableStateFlow<SignInUiState>(SignInUiState.Idle)
    val signInState = _signInState.asStateFlow()

    private val _userSignInState = MutableStateFlow<Boolean>(false)
    val userSignInState = _userSignInState.asStateFlow()

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
                        _signInState.value = SignInUiState.Loading
                    }

                    is Resource.Success -> {
                        _signInState.value = SignInUiState.Success
                    }

                    is Resource.Error -> {
                        _signInState.value = SignInUiState.Error(message = result.message)
                    }
                }
            }.launchIn(this)
        }
    }

    fun isUserSignIn() {
        viewModelScope.launch {
            isUserSignedInUseCase().onEach { result ->
                _userSignInState.value = result
            }.launchIn(this)
        }
    }
}