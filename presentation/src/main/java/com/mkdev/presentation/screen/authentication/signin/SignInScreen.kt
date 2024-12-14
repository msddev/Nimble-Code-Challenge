package com.mkdev.presentation.screen.authentication.signin

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mkdev.presentation.screen.authentication.signin.components.SignInScreenContent
import com.mkdev.presentation.viewmodel.SignInViewModel

@Composable
internal fun SignInScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToHome: () -> Unit,
) {
    signInViewModel.signIn("msd.khoshkam@gmail.com", "12345678")

    SignInScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        onForgotPasswordClick = {

        },
        onLoginClick = { email: String, password: String ->

        }
    )

    BackHandler {}
}