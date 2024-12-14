package com.mkdev.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mkdev.domain.usecase.SignInUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

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

            }.launchIn(this)
        }
    }
}