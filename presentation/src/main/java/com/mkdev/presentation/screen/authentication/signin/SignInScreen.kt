package com.mkdev.presentation.screen.authentication.signin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mkdev.presentation.common.component.AlertType
import com.mkdev.presentation.common.component.AlertView
import com.mkdev.presentation.common.component.loading.LoadingView
import com.mkdev.presentation.screen.authentication.signin.components.SignInScreenContent
import com.mkdev.presentation.viewmodel.SignInViewModel

@Composable
internal fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    when (val uiState = signInViewModel.state.collectAsStateWithLifecycle().value) {
        SignInUiState.Idle -> {

        }

        SignInUiState.Loading -> {
            LoadingView()
        }

        is SignInUiState.Success -> {
            if (uiState.data == null) {
                AlertView(
                    text = "Oops! something went wrong",
                    type = AlertType.ERROR,
                )
                return
            }

            AlertView(
                text = "Successful login",
                type = AlertType.SUCCESS,
            )

            //onNavigateToHome()
        }

        is SignInUiState.Error -> {
            AlertView(
                text = "Oops! something went wrong",
                type = AlertType.ERROR,
            )
        }
    }

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
}