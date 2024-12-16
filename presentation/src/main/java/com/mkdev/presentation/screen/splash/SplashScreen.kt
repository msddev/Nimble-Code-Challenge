package com.mkdev.presentation.screen.splash

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.mkdev.presentation.screen.splash.components.SplashScreenContent
import com.mkdev.presentation.viewmodel.SignInViewModel

@Composable
internal fun SplashScreen(
    signInViewModel: SignInViewModel = hiltViewModel(),
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    var isUserSignedIn by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        signInViewModel.isUserSignIn()
    }

    LaunchedEffect(signInViewModel.userSignInState) {
        signInViewModel.userSignInState.collect { isSignedIn ->
            isUserSignedIn = isSignedIn
        }
    }

    SplashScreenContent(
        modifier = Modifier.fillMaxSize(),
        onNavigateToSignIn = {
            if (isUserSignedIn) {
                onNavigateToHome()
            } else {
                onNavigateToSignIn()
            }
        },
    )

    BackHandler(enabled = true) {}
}