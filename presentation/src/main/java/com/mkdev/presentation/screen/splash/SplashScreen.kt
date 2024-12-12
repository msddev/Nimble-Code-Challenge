package com.mkdev.presentation.screen.splash

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.splash.components.SplashScreenContent

@Composable
internal fun SplashScreen(
    onNavigateToSignIn: () -> Unit,
) {
    SplashScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateToSignIn)
    )
}