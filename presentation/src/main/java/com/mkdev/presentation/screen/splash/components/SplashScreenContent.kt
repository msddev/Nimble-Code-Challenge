package com.mkdev.presentation.screen.splash.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.mkdev.presentation.R
import com.mkdev.presentation.theme.CommonColors
import kotlinx.coroutines.delay

@Composable
internal fun SplashScreenContent(
    modifier: Modifier,
    onNavigateToSignIn: () -> Unit,
) {
    var showLogo by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(1000)
        showLogo = true
        delay(2000)
        showLogo = false
        delay(1000)
        onNavigateToSignIn()
    }

    Box(modifier = modifier) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.img_splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(CommonColors.GradientBlack))
        )

        AnimatedVisibility(
            modifier = Modifier
                .wrapContentSize()
                .align(Alignment.Center),
            visible = showLogo,
            enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
            exit = fadeOut(animationSpec = tween(durationMillis = 1000)),
        ) {
            Image(
                painter = painterResource(R.drawable.img_nimble_logo),
                contentDescription = null,
            )
        }
    }
}