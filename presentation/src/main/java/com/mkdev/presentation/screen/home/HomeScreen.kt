package com.mkdev.presentation.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.home.components.HomeScreenContent

@Composable
internal fun HomeScreen(
    onNavigateToThankYou: () -> Unit,
) {
    HomeScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateToThankYou)
    )
}