package com.mkdev.presentation.screen.authentication.signin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.AlertType
import com.mkdev.presentation.common.component.AlertView
import com.mkdev.presentation.common.component.LoadingView
import com.mkdev.presentation.screen.authentication.signin.components.SignInScreenContent
import com.mkdev.presentation.viewmodel.SignInViewModel

@Composable
fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    val uiState = signInViewModel.signInState.collectAsStateWithLifecycle().value

    SignInScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onForgotPasswordClick = onNavigateToForgotPassword,
        onLoginClick = { email: String, password: String ->
            signInViewModel.signIn(email = email, password = password)
        }
    )

    when (uiState) {
        SignInUiState.Idle -> {}

        SignInUiState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is SignInUiState.Success -> {
            onNavigateToHome()
        }

        is SignInUiState.Error -> {
            AlertView(
                isVisible = true,
                text = uiState.message ?: stringResource(R.string.oops_something_went_wrong),
                alertType = AlertType.ERROR,
            )
        }
    }
}