package com.mkdev.data.datasource.local.database.room.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkdev.data.datasource.local.database.room.NimbleRoomDatabase
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.factory.SurveyEntityFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class SurveyDaoTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: NimbleRoomDatabase
    private lateinit var surveyDao: SurveyDao
    private lateinit var testDispatcher:TestDispatcher

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            NimbleRoomDatabase::class.java
        ).allowMainThreadQueries().build()
        surveyDao = database.surveyDao()

        testDispatcher = StandardTestDispatcher()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun `insertAll should insert surveys into database`() = runTest(testDispatcher) {
        // Given
        val surveys = SurveyEntityFactory.createSurveyEntityList(count = 2)

        // When
        surveyDao.insertAll(surveys)

        // Then
        val insertedSurvey1 = surveyDao.getById(surveys[0].id)
        val insertedSurvey2 = surveyDao.getById(surveys[1].id)

        assertEquals(surveys[0], insertedSurvey1)
        assertEquals(surveys[1], insertedSurvey2)
    }

    @Test
    fun `getByPaging should return paging source of surveys`() = runTest(testDispatcher) {
        // Given
        val surveys = SurveyEntityFactory.createSurveyEntityList(count = 2)
        surveyDao.insertAll(surveys)

        // When
        val pagingSource = surveyDao.getByPaging()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        // Then
        assertEquals(surveys, (loadResult as PagingSource.LoadResult.Page).data)
    }

    @Test
    fun `getById should return survey by id`() = runTest(testDispatcher) {
        // Given
        val survey = SurveyEntityFactory.createSurveyEntity()
        surveyDao.insertAll(listOf(survey))

        // When
        val retrievedSurvey = surveyDao.getById(survey.id)

        // Then
        assertEquals(survey, retrievedSurvey)
    }

    @Test
    fun `getById should return null when survey not found`() = runTest(testDispatcher) {
        // Given
        val nonexistentId = "nonexistent_id"

        // When
        val retrievedSurvey = surveyDao.getById(nonexistentId)

        // Then
        assertEquals(null, retrievedSurvey)
    }

    @Test
    fun `clearAll should clear all surveys from database`() = runTest(testDispatcher) {
        // Given
        val surveys = SurveyEntityFactory.createSurveyEntityList(count = 2)
        surveyDao.insertAll(surveys)

        // When
        surveyDao.clearAll()

        // Then
        val pagingSource = surveyDao.getByPaging()
        val loadResult = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )
        assertEquals(emptyList<SurveyEntity>(), (loadResult as PagingSource.LoadResult.Page).data)
    }
}