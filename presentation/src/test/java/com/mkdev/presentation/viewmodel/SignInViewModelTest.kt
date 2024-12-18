package com.mkdev.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mkdev.domain.usecase.IsUserSignedInUseCase
import com.mkdev.domain.usecase.SignInUseCase
import com.mkdev.domain.utils.Resource
import com.mkdev.presentation.screen.authentication.signin.SignInUiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SignInViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var testDispatcher: TestDispatcher

    @MockK
    private lateinit var signInUseCase: SignInUseCase

    @MockK
    private lateinit var isUserSignedInUseCase: IsUserSignedInUseCase

    private lateinit var signInViewModel: SignInViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        signInViewModel = SignInViewModel(signInUseCase, isUserSignedInUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `signIn() with success result updates signInState to Success`() = runTest(testDispatcher) {
        // Given
        val email = "test@example.com"
        val password = "password"
        val grantType: String = "password"

        coEvery {
            signInUseCase(
                grantType = grantType,
                email = email,
                password = password
            )
        } returns flowOf(Resource.Success(Unit))

        // When
        Assert.assertEquals(SignInUiState.Idle, signInViewModel.signInState.value)

        signInViewModel.signIn(email, password)

        // Then
        advanceUntilIdle() // Allow state to update
        Assert.assertEquals(SignInUiState.Success, signInViewModel.signInState.value)
    }

    @Test
    fun `signIn() with error result updates signInState to Error`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        val grantType: String = "password"
        val errorMessage = "Error message"

        coEvery {
            signInUseCase(
                grantType = grantType,
                email = email,
                password = password
            )
        } returns flowOf(Resource.Error(errorMessage))

        // When
        Assert.assertEquals(SignInUiState.Idle, signInViewModel.signInState.value)

        signInViewModel.signIn(email, password)

        // Then
        advanceUntilIdle()
        Assert.assertEquals(SignInUiState.Error(errorMessage), signInViewModel.signInState.value)
    }

    @Test
    fun `signIn() with loading result updates signInState to Loading`() = runTest(testDispatcher) {
        // Given
        val email = "test@example.com"
        val password = "password"
        val grantType: String = "password"

        coEvery {
            signInUseCase(
                grantType = grantType,
                email = email,
                password = password
            )
        } returns flowOf(Resource.Loading())

        // When
        Assert.assertEquals(SignInUiState.Idle, signInViewModel.signInState.value)

        signInViewModel.signIn(email, password)

        // Then
        advanceUntilIdle()
        Assert.assertEquals(SignInUiState.Loading, signInViewModel.signInState.value)
    }

    @Test
    fun `isUserSignIn() updates userSignInState with result`() = runTest(testDispatcher) {
        // Given
        val isSignedIn = true // Expected result from use case

        coEvery { isUserSignedInUseCase() } returns flowOf(isSignedIn)

        // When
        signInViewModel.isUserSignIn()

        // Then
        advanceUntilIdle()

        // Final state assertion
        Assert.assertEquals(isSignedIn, signInViewModel.userSignInState.value)
    }
}