package com.mkdev.presentation.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun ErrorView(
    modifier: Modifier = Modifier,
    text: String,
    actionButtonText: String = stringResource(R.string.retry),
    onAction: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = Dimens.PaddingStandard),
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )

        Icon(
            imageVector = Icons.Filled.Face,
            contentDescription = null,
            modifier = Modifier
                .size(Dimens.IconSizeXLarge)
                .padding(top = Dimens.PaddingStandard),
            tint = Color.White
        )

        Button(
            modifier = Modifier.padding(top = Dimens.PaddingStandard),
            onClick = onAction
        ) {
            Text(text = actionButtonText)
        }
    }
}
