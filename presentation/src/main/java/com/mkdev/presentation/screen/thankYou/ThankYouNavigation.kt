package com.mkdev.presentation.screen.thankYou

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

private object ThankYouNavigation {
    const val ROUTE = "thank_you"
}

internal fun NavGraphBuilder.thankYouRoute(
    onNavigateUp: () -> Unit,
) {
    composable(
        route = ThankYouNavigation.ROUTE,
    ) {
        ThankYouScreen(onNavigateUp = onNavigateUp)
    }
}

internal fun NavHostController.navigateToThankYouRoute(navOptions: NavOptions? = null) {
    navigate(route = ThankYouNavigation.ROUTE, navOptions = navOptions)
}
