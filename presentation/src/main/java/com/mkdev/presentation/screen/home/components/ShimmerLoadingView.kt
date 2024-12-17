package com.mkdev.presentation.screen.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mkdev.presentation.common.component.ShimmerEffect
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun ShimmerLoadingView(
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .statusBarsPadding()
            .padding(Dimens.PaddingStandard)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = Dimens.PaddingLarge),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterHorizontally
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                ShimmerEffect(
                    modifier = Modifier
                        .width(140.dp)
                        .height(Dimens.PagerIndicatorWidth)
                        .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                        .clip(CircleShape)

                )

                Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

                ShimmerEffect(
                    modifier = Modifier
                        .width(100.dp)
                        .height(Dimens.PagerIndicatorWidth)
                        .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                        .clip(CircleShape)

                )
            }

            ShimmerEffect(
                modifier = Modifier
                    .size(Dimens.IconSize2XLarge)
                    .background(Color.DarkGray, shape = CircleShape)
                    .clip(CircleShape)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ShimmerEffect(
                modifier = Modifier
                    .width(60.dp)
                    .height(Dimens.PagerIndicatorWidth)
                    .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = Dimens.PaddingXLarge)
                    .height(Dimens.PagerIndicatorWidth)
                    .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(Dimens.PaddingSmall))

            ShimmerEffect(
                modifier = Modifier
                    .width(140.dp)
                    .height(Dimens.PagerIndicatorWidth)
                    .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(Dimens.PaddingStandard))

            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = Dimens.PaddingSmall)
                    .height(Dimens.PagerIndicatorWidth)
                    .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(Dimens.PaddingSmall))

            ShimmerEffect(
                modifier = Modifier
                    .width(180.dp)
                    .height(Dimens.PagerIndicatorWidth)
                    .background(Color.DarkGray, RoundedCornerShape(Dimens.CornerRadiusXLarge))
                    .clip(CircleShape)

            )

            Spacer(modifier = Modifier.height(Dimens.Padding2xLarge))
        }
    }
}