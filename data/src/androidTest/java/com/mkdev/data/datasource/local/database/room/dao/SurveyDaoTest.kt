package com.mkdev.data.datasource.local.database.room.dao

import android.content.Context
import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mkdev.data.datasource.local.database.room.NimbleRoomDatabase
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.factory.SurveyEntityFactory
import com.mkdev.data.utils.TestDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyDaoTest {

    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var database: NimbleRoomDatabase
    private lateinit var surveyDao: SurveyDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            NimbleRoomDatabase::class.java
        ).build()
        surveyDao = database.surveyDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll_should_insert_surveys_into_database() = runTest {
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
    fun getByPaging_should_return_paging_source_of_surveys() = runTest {
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
    fun getById_should_return_survey_by_id() = runTest {
        // Given
        val survey = SurveyEntityFactory.createSurveyEntity()
        surveyDao.insertAll(listOf(survey))

        // When
        val retrievedSurvey = surveyDao.getById(survey.id)

        // Then
        assertEquals(survey, retrievedSurvey)
    }

    @Test
    fun getById_should_return_null_when_survey_not_found() = runTest {
        // Given
        val nonexistentId = "nonexistent_id"

        // When
        val retrievedSurvey = surveyDao.getById(nonexistentId)

        // Then
        assertEquals(null, retrievedSurvey)
    }

    @Test
    fun clearAll_should_clear_all_surveys_from_database() = runTest {
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