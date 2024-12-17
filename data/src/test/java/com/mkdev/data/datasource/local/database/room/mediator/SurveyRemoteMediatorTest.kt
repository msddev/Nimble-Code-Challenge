package com.mkdev.data.datasource.local.database.room.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.RemoteMediator.InitializeAction
import com.mkdev.data.datasource.local.database.room.dao.SurveyDao
import com.mkdev.data.datasource.local.database.room.dao.SurveyRemoteKeyDao
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.local.mapper.SurveyEntityMapper
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import com.mkdev.data.factory.SurveyEntityFactory
import com.mkdev.data.factory.SurveyRemoteKeyEntityFactory
import com.mkdev.data.factory.SurveyResponseFactory
import com.mkdev.data.utils.RemoteApiPaging
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
@RunWith(MockitoJUnitRunner::class)
class SurveyRemoteMediatorTest {

    @Mock
    private lateinit var surveyApi: SurveyApi

    @Mock
    private lateinit var surveyDao: SurveyDao

    @Mock
    private lateinit var surveyRemoteKeyDao: SurveyRemoteKeyDao

    @Mock
    private lateinit var surveyEntityMapper: SurveyEntityMapper

    private lateinit var surveyRemoteMediator: SurveyRemoteMediator
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        surveyRemoteMediator = SurveyRemoteMediator(
            surveyApi,
            surveyDao,
            surveyRemoteKeyDao,
            surveyEntityMapper
        )
    }

    @Test
    fun `refresh load returns success result when more data is present`() =
        runTest(testDispatcher) {
            // Given
            val surveyResponse = SurveyResponseFactory.createSurveyResponse()
            val surveyEntity = SurveyEntityFactory.createSurveyEntity()
            val pagingState = PagingState<Int, SurveyEntity>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )
            `when`(surveyApi.getSurveys(RemoteApiPaging.FIRST_PAGE, RemoteApiPaging.PAGE_SIZE))
                .thenReturn(Response.success(BaseApiResponse(listOf(surveyResponse), null)))
            `when`(surveyEntityMapper.mapToSurveyEntity(surveyResponse)).thenReturn(surveyEntity)

            // When
            val result = surveyRemoteMediator.load(LoadType.REFRESH, pagingState)

            // Then
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `refresh load returns success result with end of pagination reached when more data is not present`() =
        runTest(testDispatcher) {
            // Given
            val pagingState = PagingState<Int, SurveyEntity>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )
            `when`(surveyApi.getSurveys(RemoteApiPaging.FIRST_PAGE, RemoteApiPaging.PAGE_SIZE))
                .thenReturn(Response.success(BaseApiResponse(null, null)))

            // When
            val result = surveyRemoteMediator.load(LoadType.REFRESH, pagingState)

            // Then
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `refresh load returns error result when error occurs`() = runTest(testDispatcher) {
        // Given
        val pagingState = PagingState<Int, SurveyEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        `when`(surveyApi.getSurveys(RemoteApiPaging.FIRST_PAGE, RemoteApiPaging.PAGE_SIZE))
            .thenThrow(RuntimeException())

        // When
        val result = surveyRemoteMediator.load(LoadType.REFRESH, pagingState)

        // Then
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `prepend load returns success result when more data is present`() =
        runTest(testDispatcher) {
            // Given
            val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(prevPage = 1)
            val surveyResponse = SurveyResponseFactory.createSurveyResponse()
            val surveyEntity = SurveyEntityFactory.createSurveyEntity()
            val pagingState = PagingState<Int, SurveyEntity>(
                listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
                null,
                PagingConfig(10),
                10
            )
            `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)
            `when`(surveyApi.getSurveys(1, RemoteApiPaging.PAGE_SIZE))
                .thenReturn(Response.success(BaseApiResponse(listOf(surveyResponse), null)))
            `when`(surveyEntityMapper.mapToSurveyEntity(surveyResponse)).thenReturn(surveyEntity)

            // When
            val result = surveyRemoteMediator.load(LoadType.PREPEND, pagingState)

            // Then
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `prepend load returns success result with end of pagination reached when more data is not present`() =
        runTest(testDispatcher) {
            // Given
            val remoteKeys =
                SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(prevPage = null)
            val surveyEntity = SurveyEntityFactory.createSurveyEntity()
            val pagingState = PagingState<Int, SurveyEntity>(
                listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
                null,
                PagingConfig(10),
                10
            )
            `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)

            // When
            val result = surveyRemoteMediator.load(LoadType.PREPEND, pagingState)

            // Then
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `prepend load returns error result when error occurs`() = runTest(testDispatcher) {
        // Given
        val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(prevPage = 1)
        val surveyEntity = SurveyEntityFactory.createSurveyEntity()
        val pagingState = PagingState<Int, SurveyEntity>(
            listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
            null,
            PagingConfig(10),
            10
        )
        `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)
        `when`(surveyApi.getSurveys(1, RemoteApiPaging.PAGE_SIZE))
            .thenThrow(RuntimeException())

        // When
        val result = surveyRemoteMediator.load(LoadType.PREPEND, pagingState)

        // Then
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `append load returns success result when more data is present`() = runTest(testDispatcher) {
        // Given
        val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(nextPage = 2)
        val surveyResponse = SurveyResponseFactory.createSurveyResponse()
        val surveyEntity = SurveyEntityFactory.createSurveyEntity()
        val pagingState = PagingState<Int, SurveyEntity>(
            listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
            null,
            PagingConfig(10),
            10
        )
        `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)
        `when`(surveyApi.getSurveys(2, RemoteApiPaging.PAGE_SIZE))
            .thenReturn(Response.success(BaseApiResponse(listOf(surveyResponse), null)))
        `when`(surveyEntityMapper.mapToSurveyEntity(surveyResponse)).thenReturn(surveyEntity)

        // When
        val result = surveyRemoteMediator.load(LoadType.APPEND, pagingState)

        // Then
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `append load returns success result with end of pagination reached when more data is not present`() =
        runTest(testDispatcher) {
            // Given
            val remoteKeys =
                SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(nextPage = null)
            val surveyEntity = SurveyEntityFactory.createSurveyEntity()
            val pagingState = PagingState<Int, SurveyEntity>(
                listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
                null,
                PagingConfig(10),
                10
            )
            `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)

            // When
            val result = surveyRemoteMediator.load(LoadType.APPEND, pagingState)

            // Then
            Assert.assertTrue(result is RemoteMediator.MediatorResult.Success)
            Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `append load returns error result when error occurs`() = runTest(testDispatcher) {
        // Given
        val remoteKeys = SurveyRemoteKeyEntityFactory.createSurveyRemoteKeyEntity(nextPage = 2)
        val surveyEntity = SurveyEntityFactory.createSurveyEntity()
        val pagingState = PagingState<Int, SurveyEntity>(
            listOf(PagingSource.LoadResult.Page(listOf(surveyEntity), null, 2)),
            null,
            PagingConfig(10),
            10
        )
        `when`(surveyRemoteKeyDao.remoteKeysId(surveyEntity.id)).thenReturn(remoteKeys)
        `when`(surveyApi.getSurveys(2, RemoteApiPaging.PAGE_SIZE))
            .thenThrow(RuntimeException())

        // When
        val result = surveyRemoteMediator.load(LoadType.APPEND, pagingState)

        // Then
        Assert.assertTrue(result is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `initialize returns LAUNCH_INITIAL_REFRESH`() = runTest(testDispatcher) {
        // Given & When
        val initializeAction = surveyRemoteMediator.initialize()

        // Then
        Assert.assertEquals(InitializeAction.LAUNCH_INITIAL_REFRESH, initializeAction)
    }
}