package com.mkdev.presentation.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.CommonColors
import com.mkdev.presentation.theme.Dimens
import kotlinx.coroutines.delay

enum class AlertType {
    SUCCESS, ERROR
}

private object AlertDefaults {

    val shape
        @Composable get() = RoundedCornerShape(10.dp)

    @Composable
    fun contentColor(alertType: AlertType) = when (alertType) {
        AlertType.SUCCESS -> CommonColors.Success
        AlertType.ERROR -> CommonColors.Error
    }

    @Composable
    fun containerColor(alertType: AlertType) = when (alertType) {
        AlertType.SUCCESS -> CommonColors.Success.copy(alpha = 0.2f)
        AlertType.ERROR -> CommonColors.Error.copy(alpha = 0.2f)
    }

    fun iconResId(alertType: AlertType) = when (alertType) {
        AlertType.SUCCESS -> R.drawable.ic_tick_square
        AlertType.ERROR -> R.drawable.ic_info_circle
    }

}

@Composable
internal fun AlertView(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    text: String,
    alertType: AlertType,
) {
    var alertVisibility by remember { mutableStateOf<Boolean>(isVisible) }

    if (alertVisibility) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(Dimens.PaddingStandard),
        ) {
            AlertContent(
                modifier = modifier,
                text = text,
                alertType = alertType
            )
        }
    }

    LaunchedEffect(isVisible) {
        delay(5000)
        alertVisibility = false
    }
}

@Composable
private fun BoxScope.AlertContent(
    modifier: Modifier = Modifier,
    text: String,
    alertType: AlertType,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(AlertDefaults.shape)
            .background(AlertDefaults.containerColor(alertType = alertType))
            .align(Alignment.TopCenter),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = Dimens.PaddingMedium,
                vertical = Dimens.PaddingSmall
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(Dimens.IconSizeMedium),
                painter = painterResource(id = AlertDefaults.iconResId(alertType)),
                contentDescription = null,
                tint = AlertDefaults.contentColor(alertType)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
            )
        }
    }
}