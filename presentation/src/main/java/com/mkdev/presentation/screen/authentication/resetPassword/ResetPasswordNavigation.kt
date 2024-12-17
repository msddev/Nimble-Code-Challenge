package com.mkdev.presentation.screen.authentication.resetPassword

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

object ResetPasswordNavigation {
    const val ROUTE = "reset_password"
}

fun NavGraphBuilder.resetPasswordRoute(
    onNavigateUp: () -> Unit,
) {
    composable(
        route = ResetPasswordNavigation.ROUTE,
    ) {
        ResetPasswordScreen(onNavigateUp = onNavigateUp)
    }
}

fun NavHostController.navigateToResetPasswordRoute(navOptions: NavOptions? = null) {
    navigate(route = ResetPasswordNavigation.ROUTE, navOptions = navOptions)
}
