package com.mkdev.presentation.screen.thankYou

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.thankYou.components.ThankYouScreenContent
import com.mkdev.presentation.theme.CommonColors
import kotlinx.coroutines.delay

@Composable
internal fun ThankYouScreen(
    onNavigateUp: () -> Unit,
) {
    ThankYouScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .background(CommonColors.ThankYouBackColor)
    )

    LaunchedEffect(key1 = Unit) {
        delay(5000)
        onNavigateUp()
    }
}