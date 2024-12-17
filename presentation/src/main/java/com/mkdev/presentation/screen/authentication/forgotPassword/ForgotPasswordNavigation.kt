package com.mkdev.presentation.screen.authentication.forgotPassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

object ForgotPasswordNavigation {
    const val ROUTE = "forgot_password"
}

fun NavGraphBuilder.forgotPasswordRoute(
    onNavigateUp: () -> Unit,
) {
    composable(
        route = ForgotPasswordNavigation.ROUTE,
    ) {
        ForgotPasswordScreen(onNavigateUp = onNavigateUp)
    }
}

fun NavHostController.navigateToForgotPasswordRoute(navOptions: NavOptions? = null) {
    navigate(route = ForgotPasswordNavigation.ROUTE, navOptions = navOptions)
}
