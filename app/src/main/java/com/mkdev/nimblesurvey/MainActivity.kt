package com.mkdev.nimblesurvey

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.mkdev.presentation.navigation.NimbleNavHost
import com.mkdev.presentation.screen.home.HomeNavigation
import com.mkdev.presentation.screen.thankYou.ThankYouNavigation
import com.mkdev.presentation.theme.NimbleSurveyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NimbleSurveyTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NimbleNavHost(
                        navController = navController,
                        startDestination = ThankYouNavigation.ROUTE
                    )
                }
            }
        }


    }
}