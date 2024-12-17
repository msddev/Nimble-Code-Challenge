package com.mkdev.presentation.screen.authentication.resetPassword

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
import com.mkdev.presentation.screen.authentication.resetPassword.components.ResetPasswordScreenContent
import com.mkdev.presentation.viewmodel.ResetPasswordViewModel

@Composable
internal fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onNavigateUp: () -> Unit,
) {
    val uiState = viewModel.resetPasswordState.collectAsStateWithLifecycle().value

    ResetPasswordScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onResetClick = { email: String ->
            viewModel.resetPassword(email = email)
        },
        onBackClick = onNavigateUp
    )

    when (uiState) {
        ResetPasswordUiState.Idle -> {}

        ResetPasswordUiState.Loading -> {
            LoadingView(modifier = Modifier.fillMaxSize())
        }

        is ResetPasswordUiState.Success -> {
            AlertView(
                isVisible = true,
                text = uiState.message ?: stringResource(R.string.we_have_email_new_password),
                alertType = AlertType.SUCCESS,
                lengthTime = 10000,
            )
        }

        is ResetPasswordUiState.Error -> {
            AlertView(
                isVisible = true,
                text = uiState.message ?: stringResource(R.string.oops_something_went_wrong),
                alertType = AlertType.ERROR,
            )
        }
    }
}