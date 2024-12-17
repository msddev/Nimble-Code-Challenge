package com.mkdev.data.datasource.local.datastore

import androidx.datastore.core.DataStore
import com.ibm.icu.impl.Assert.fail
import com.mkdev.data.datasource.local.UserLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UserLocalSourceTest {

    @Mock
    private lateinit var dataStore: DataStore<UserLocal>

    private lateinit var userLocalSource: UserLocalSourceImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        userLocalSource = UserLocalSourceImpl(dataStore)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `user should emit UserLocal object when data store emits non-null value`() = runTest {
        // Given
        val userLocal = UserLocal.newBuilder()
            .setAccessToken("access_token")
            .setCreatedAt("created_at")
            .setExpiresIn("expires_in")
            .setRefreshToken("refresh_token")
            .setTokenType("token_type")
            .build()
        val flow = flowOf(userLocal)
        `when`(dataStore.data).thenReturn(flow)

        // When
        val result = userLocalSource.user().first()

        // Then
        Assert.assertEquals(userLocal, result)
    }

    @Test
    fun `user should emit null when data store emits USER_LOCAL_NULL`() = runTest {
        // Given
        val flow = flowOf(USER_LOCAL_NULL)
        `when`(dataStore.data).thenReturn(flow)

        // When
        val result = userLocalSource.user().first()

        // Then
        Assert.assertNull(result)
    }

    @Test
    fun `user should emit null when data store throws IOException`() = runTest {
        // Given
        val flow = flow<UserLocal> { throw IOException() }
        `when`(dataStore.data).thenReturn(flow)

        // When
        val result = userLocalSource.user().first()

        // Then
        Assert.assertNull(result)
    }

    @Test(expected = RuntimeException::class)
    fun `user should rethrow exception when data store throws non-IOException`() = runTest {
        // Given
        val flow = flow<UserLocal> { throw RuntimeException() }
        `when`(dataStore.data).thenReturn(flow)

        // When & Then
        try {
            userLocalSource.user().first()
            fail("Expected RuntimeException to be thrown") // Fail if exception is not thrown
        } catch (e: RuntimeException) {
            throw e  // Rethrow the exception
        }
    }
}