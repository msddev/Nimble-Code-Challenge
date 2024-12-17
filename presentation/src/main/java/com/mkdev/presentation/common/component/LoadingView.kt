package com.mkdev.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun LoadingView(
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .clickable(enabled = false, onClick = {})
            .background(color = Color.Black.copy(alpha = 0.5f)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(Dimens.IconSizeXLarge)
                .padding(top = Dimens.PaddingStandard),
            color = Color.White,
        )
    }
}
