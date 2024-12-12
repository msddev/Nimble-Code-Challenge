package com.mkdev.presentation.screen.authentication.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.authentication.signin.components.SignInScreenContent

@Composable
internal fun SignInScreen(
    onNavigateToHome: () -> Unit,
) {
    SignInScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateToHome)
    )
}