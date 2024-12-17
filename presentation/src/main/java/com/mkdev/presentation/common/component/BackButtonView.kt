package com.mkdev.presentation.common.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.mkdev.presentation.R

@Composable
internal fun TopAppBarBackButton(onClick: () -> Unit) {
    IconButton(
        onClick = {
            onClick.invoke()
        }
    ) {
        Icon(
            painter = painterResource(R.drawable.arrow_back_24),
            contentDescription = null,
            tint = Color.White
        )
    }
}