package com.mkdev.data.datasource.local.database.room.dao//package com.mkdev.data.datasource.local.database.room.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkdev.data.datasource.local.database.room.NimbleRoomDatabase
import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity
import com.mkdev.data.factory.SurveyRemoteKeyEntityFactory
import com.mkdev.data.utils.TestDispatcherRule
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyRemoteKeyDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var database: NimbleRoomDatabase
    private lateinit var surveyRemoteKeyDao: SurveyRemoteKeyDao
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NimbleRoomDatabase::class.java
        ).build()
        surveyRemoteKeyDao = database.surveyRemoteKeyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll_should_insert_remote_keys_into_database() = runTest {
        // Given
        val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntityList(count = 2)

        // When
        surveyRemoteKeyDao.insertAll(remoteKeys)

        // Then
        val allRemoteKeys = surveyRemoteKeyDao.getAll()
        Assert.assertEquals(remoteKeys, allRemoteKeys)
    }

    @Test
    fun insert_should_insert_a_single_remote_key_into_database() = runTest(testDispatcher) {
        // Given
        val remoteKey = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity()

        // When
        surveyRemoteKeyDao.insert(remoteKey)

        // Then
        val retrievedRemoteKey = surveyRemoteKeyDao.remoteKeysId(remoteKey.surveyId)
        Assert.assertEquals(remoteKey, retrievedRemoteKey)
    }

    @Test
    fun insertOrReplace_should_insert_or_replace_a_remote_key() = runTest(testDispatcher) {
        // Given
        val remoteKey1 = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity()
        val remoteKey2 = remoteKey1.copy(nextPage = 3) // Updated nextPage

        // When
        surveyRemoteKeyDao.insertOrReplace(remoteKey1)
        surveyRemoteKeyDao.insertOrReplace(remoteKey2) // Replace existing key

        // Then
        val retrievedRemoteKey = surveyRemoteKeyDao.remoteKeysId(remoteKey1.surveyId)
        Assert.assertEquals(remoteKey2, retrievedRemoteKey) // Verify replaced key
    }

    @Test
    fun remoteKeysId_should_return_remote_key_by_id() = runTest(testDispatcher) {
        // Given
        val remoteKey = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity()
        surveyRemoteKeyDao.insert(remoteKey)

        // When
        val retrievedRemoteKey = surveyRemoteKeyDao.remoteKeysId(remoteKey.surveyId)

        // Then
        Assert.assertEquals(remoteKey, retrievedRemoteKey)
    }

    @Test
    fun remoteKeysId_should_return_null_when_remote_key_not_found() = runTest(testDispatcher) {
        // Given
        val nonexistentId = "nonexistent_id"

        // When
        val retrievedRemoteKey = surveyRemoteKeyDao.remoteKeysId(nonexistentId)

        // Then
        Assert.assertEquals(null, retrievedRemoteKey)
    }

    @Test
    fun clearRemoteKeys_should_clear_all_remote_keys_from_database() = runTest(testDispatcher) {
        // Given
        val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntityList(count = 2)
        surveyRemoteKeyDao.insertAll(remoteKeys)

        // When
        surveyRemoteKeyDao.clearRemoteKeys()

        // Then
        val allRemoteKeys = surveyRemoteKeyDao.getAll()
        Assert.assertEquals(emptyList<SurveyRemoteKeyEntity>(), allRemoteKeys)
    }
}