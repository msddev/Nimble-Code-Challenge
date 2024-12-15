package com.mkdev.presentation.theme

import androidx.compose.ui.graphics.Color

object LightColors {
    val Primary = Color(0xFFD0BCFF)
    val Secondary = Color(0xFFCCC2DC)
    val Tertiary = Color(0xFFEFB8C8)
    val Background = Color(0xFFF5F5F5)
    val Error = Color(0xFFF44336)
}

object DarkColors {
    val Primary = Color(0xFF6650a4)
    val Secondary = Color(0xFF625b71)
    val Tertiary = Color(0xFF625b71)
    val Background = Color(0xFF303030)
    val Error = Color(0xFFCF6679)
}

object CommonColors {
    val GradientBlack1 = listOf(
        Color.Black.copy(alpha = 0.1f),
        Color.Black
    )
    val GradientBlack2 = listOf(
        Color.Black.copy(alpha = 0.5f),
        Color.Black.copy(alpha = 0.9f),
        Color.Black
    )

    val Green: Color = Color(0xFF4AAF57)
    val LightGreen: Color = Color(0xFF8BC255)
    val Lime: Color = Color(0xFFCDDC4C)
    val ThankYouBackColor: Color = Color(0xFF15151A)

    // Alert & Status
    val Success: Color = Color(0xFF4ADE80)
    val Info: Color = Color(0xFF246BFD)
    val Warning: Color = Color(0xFFFACC15)
    val Error: Color = Color(0xFFF75555)
    val Disabled: Color = Color(0xFFD8D8D8)
    val ButtonDisabled: Color = Color(0xFF53A777)
}