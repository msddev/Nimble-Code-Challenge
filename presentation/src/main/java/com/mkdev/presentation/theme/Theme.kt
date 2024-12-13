package com.mkdev.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DarkColors.Primary,
    secondary = DarkColors.Secondary,
    tertiary = DarkColors.Tertiary,
    background = DarkColors.Background,
    error = DarkColors.Error,
)

private val LightColorScheme = lightColorScheme(
    primary = LightColors.Primary,
    secondary = LightColors.Secondary,
    tertiary = LightColors.Tertiary,
    background = LightColors.Background,
    error = LightColors.Error,
)

@Composable
fun NimbleSurveyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}