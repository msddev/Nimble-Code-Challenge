package com.mkdev.presentation.screen.home

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.home.components.HomeScreenContent

@Composable
internal fun HomeScreen(
    onNavigateToThankYou: () -> Unit,
) {

    var backgroundImageUrl = remember { mutableStateOf<String?>(null) }

    HomeScreenContent(
        modifier = Modifier
            .fillMaxSize(),
    )

    BackHandler {}
}