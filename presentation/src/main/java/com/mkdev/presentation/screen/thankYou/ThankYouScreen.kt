package com.mkdev.presentation.screen.thankYou

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mkdev.presentation.screen.thankYou.components.ThankYouScreenContent

@Composable
internal fun ThankYouScreen(
    onNavigateUp: () -> Unit,
) {
    ThankYouScreenContent(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onNavigateUp)
    )
}