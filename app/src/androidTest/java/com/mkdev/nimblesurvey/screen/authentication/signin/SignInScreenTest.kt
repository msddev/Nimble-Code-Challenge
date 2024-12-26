package com.mkdev.nimblesurvey.screen.authentication.signin

import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkdev.nimblesurvey.MainActivity
import com.mkdev.presentation.R
import com.mkdev.presentation.navigation.NimbleNavHost
import com.mkdev.presentation.screen.authentication.signin.SignInNavigation
import com.mkdev.presentation.theme.NimbleSurveyTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class SignInScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private lateinit var testDispatcher: TestDispatcher

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        hiltRule.inject()

        testDispatcher = StandardTestDispatcher()

        composeTestRule.activity.setContent {
            NimbleSurveyTheme {
                navController = TestNavHostController(LocalContext.current).apply {
                    navigatorProvider.addNavigator(ComposeNavigator())
                }
                NimbleNavHost(
                    navController = navController,
                    startDestination = SignInNavigation.ROUTE
                )
            }
        }
    }

    @Test
    fun signInScreen_displaysCorrectly() {
        composeTestRule.onNodeWithContentDescription("Nimble Logo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Email").assertIsDisplayed()
        composeTestRule.onNodeWithText("Password").assertIsDisplayed()
        composeTestRule.onNodeWithText("Forgot?").assertIsDisplayed()
        composeTestRule.onNodeWithText("Log in").assertIsDisplayed()
    }

    @Test
    fun signInScreen_validInput_navigatesToHome() {
        composeTestRule.onNodeWithText("Email").performTextInput("msd.khoshkam@gmail.com")
        composeTestRule.onNodeWithText("Password").performTextInput("12345678")
        composeTestRule.onNodeWithText("Log in").performClick()
    }

    @Test
    fun signInScreen_invalidInput_displaysError() {
        composeTestRule.onNodeWithText("Email").performTextInput("")
        composeTestRule.onNodeWithText("Password").performTextInput("")
        composeTestRule.onNodeWithText("Log in").performClick()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.email_empty_error))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.password_empty_error))
            .assertIsDisplayed()
    }

    @Test
    fun signInScreen_forgotPasswordClick_navigatesToForgotPassword() {
        composeTestRule.onNodeWithText("Forgot?").performClick()
    }
}
