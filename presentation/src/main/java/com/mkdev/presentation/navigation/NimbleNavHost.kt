package com.mkdev.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mkdev.presentation.screen.authentication.resetPassword.navigateToResetPasswordRoute
import com.mkdev.presentation.screen.authentication.resetPassword.resetPasswordRoute
import com.mkdev.presentation.screen.authentication.signin.navigateToSignInRoute
import com.mkdev.presentation.screen.authentication.signin.signInRoute
import com.mkdev.presentation.screen.home.homeRoute
import com.mkdev.presentation.screen.home.navigateToHomeRoute
import com.mkdev.presentation.screen.splash.splashRoute
import com.mkdev.presentation.screen.thankYou.navigateToThankYouRoute
import com.mkdev.presentation.screen.thankYou.thankYouRoute

@Composable
fun NimbleNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        splashRoute(
            onNavigateToSignIn = navController::navigateToSignInRoute,
            onNavigateToHome = navController::navigateToHomeRoute,
        )
        signInRoute(
            onNavigateToHome = navController::navigateToHomeRoute,
            onNavigateToForgotPassword = navController::navigateToResetPasswordRoute,
        )
        resetPasswordRoute(onNavigateUp = navController::navigateUp)
        homeRoute(
            onNavigateToThankYou = navController::navigateToThankYouRoute,
        )
        thankYouRoute(
            onNavigateUp = navController::navigateUp
        )
    }
}