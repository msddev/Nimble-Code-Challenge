package com.mkdev.presentation.screen.authentication.forgotPassword

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mkdev.presentation.screen.authentication.forgotPassword.components.ForgotPasswordScreenContent
import com.mkdev.presentation.viewmodel.ForgotPasswordViewModel

@Composable
internal fun ForgotPasswordScreen(
    forgotPasswordViewModel: ForgotPasswordViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
) {
    //val uiState = signInViewModel.signInState.collectAsStateWithLifecycle().value

    ForgotPasswordScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onResetClick = { email: String ->

        },
        onBackClick = onNavigateUp
    )

    /*when (uiState) {
        SignInUiState.Idle -> {}

        SignInUiState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is SignInUiState.Success -> {
            //onNavigateToHome()
        }

        is SignInUiState.Error -> {
            AlertView(
                isVisible = true,
                text = uiState.message ?: stringResource(R.string.oops_something_went_wrong),
                alertType = AlertType.ERROR,
            )
        }
    }*/
}