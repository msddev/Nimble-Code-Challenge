package com.mkdev.presentation.screen.splash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.splash.components.SplashScreenContent

@Composable
internal fun SplashScreen(
    onNavigateToSignIn: () -> Unit,
) {
    SplashScreenContent(
        modifier = Modifier.fillMaxSize(),
        onNavigateToSignIn = onNavigateToSignIn,
    )

    BackHandler(enabled = true) {}
}