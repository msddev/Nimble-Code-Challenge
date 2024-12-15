package com.mkdev.presentation.screen.authentication.signin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mkdev.presentation.R
import com.mkdev.presentation.common.component.AlertType
import com.mkdev.presentation.common.component.AlertView
import com.mkdev.presentation.common.component.loading.LoadingView
import com.mkdev.presentation.screen.authentication.signin.components.SignInScreenContent
import com.mkdev.presentation.viewmodel.SignInViewModel
import kotlinx.coroutines.delay

@Composable
internal fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    val uiState = signInViewModel.state.collectAsStateWithLifecycle().value

    SignInScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onForgotPasswordClick = {},
        onLoginClick = { email: String, password: String ->
            signInViewModel.signIn(email = email, password = password)
        }
    )

    BackHandler {}

    when (uiState) {
        SignInUiState.Idle -> {}

        SignInUiState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is SignInUiState.Success -> {
            if (uiState.data == null) {
                AlertView(
                    isVisible = true,
                    text = stringResource(R.string.oops_something_went_wrong),
                    alertType = AlertType.ERROR,
                )
                return
            }

            LaunchedEffect(Unit) {
                delay(1000)
                onNavigateToHome()
            }
        }

        is SignInUiState.Error -> {
            AlertView(
                isVisible = true,
                text = stringResource(R.string.oops_something_went_wrong),
                alertType = AlertType.ERROR,
            )
        }
    }
}