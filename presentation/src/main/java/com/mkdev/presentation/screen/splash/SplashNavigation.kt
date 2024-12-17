package com.mkdev.presentation.screen.splash

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

object SplashNavigation {
    const val ROUTE = "splash"
}

internal fun NavGraphBuilder.splashRoute(
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    composable(
        route = SplashNavigation.ROUTE,
    ) {
        SplashScreen(
            onNavigateToSignIn = onNavigateToSignIn,
            onNavigateToHome = onNavigateToHome,
        )
    }
}
