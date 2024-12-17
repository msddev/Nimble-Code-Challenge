package com.mkdev.presentation.screen.authentication.signin

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

object SignInNavigation {
    const val ROUTE = "sign_in"
}

fun NavGraphBuilder.signInRoute(
    onNavigateToHome: () -> Unit,
    onNavigateToForgotPassword: () -> Unit,
) {
    composable(
        route = SignInNavigation.ROUTE,
    ) {
        SignInScreen(
            onNavigateToHome = onNavigateToHome,
            onNavigateToForgotPassword = onNavigateToForgotPassword
        )
    }
}

fun NavHostController.navigateToSignInRoute(navOptions: NavOptions? = null) {
    navigate(route = SignInNavigation.ROUTE, navOptions = navOptions)
}
