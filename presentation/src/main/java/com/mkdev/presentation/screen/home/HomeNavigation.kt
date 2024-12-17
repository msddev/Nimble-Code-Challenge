package com.mkdev.presentation.screen.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

private object HomeNavigation {
    const val ROUTE = "home"
}

internal fun NavGraphBuilder.homeRoute(
    onNavigateToThankYou: () -> Unit,
) {
    composable(route = HomeNavigation.ROUTE) {
        HomeScreen(
            onNavigateToThankYou = onNavigateToThankYou,
        )
    }
}

internal fun NavHostController.navigateToHomeRoute(navOptions: NavOptions? = null) {
    navigate(route = HomeNavigation.ROUTE, navOptions = navOptions)
}