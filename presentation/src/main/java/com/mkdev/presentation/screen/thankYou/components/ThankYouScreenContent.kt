package com.mkdev.presentation.screen.thankYou.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.Dimens

@Composable
internal fun ThankYouScreenContent(
    modifier: Modifier,
) {
    val lottieSuccessComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_success)
    )
    val lottieHappyComposition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.animation_happy)
    )

    val lottieHappyProgress by animateLottieCompositionAsState(
        composition = lottieHappyComposition,
        iterations = LottieConstants.IterateForever
    )

    Box(modifier = modifier) {

        Column(
            modifier = modifier
                .padding(Dimens.PaddingStandard)
                .align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                modifier = Modifier.size(Dimens.ThankYouImageSize),
                composition = lottieSuccessComposition,
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.thanks_for_taking_the_survey),
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            )
        }

        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            composition = lottieHappyComposition,
            progress = {
                lottieHappyProgress
            }
        )

        LottieAnimation(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .rotate(180f),
            composition = lottieHappyComposition,
            progress = {
                lottieHappyProgress
            }
        )
    }
}