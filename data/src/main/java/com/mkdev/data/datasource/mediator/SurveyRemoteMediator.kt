package com.mkdev.data.datasource.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.mkdev.data.datasource.local.database.room.dao.SurveyDao
import com.mkdev.data.datasource.local.database.room.dao.SurveyRemoteKeyDao
import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.local.database.room.entity.SurveyRemoteKeyEntity
import com.mkdev.data.datasource.local.mapper.SurveyEntityMapper
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.utils.RemoteApiPaging
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
internal class SurveyRemoteMediator(
    private val surveyApi: SurveyApi,
    private val surveyDao: SurveyDao,
    private val surveyRemoteKeyDao: SurveyRemoteKeyDao,
    private val surveyEntityMapper: SurveyEntityMapper,
) : RemoteMediator<Int, SurveyEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SurveyEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: RemoteApiPaging.FIRST_PAGE
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            // Check if local data is available for REFRESH
            if (loadType == LoadType.REFRESH) {
                val localSurveys = surveyDao.getSurveysCount()
                if (localSurveys > 0) {
                    return MediatorResult.Success(endOfPaginationReached = false)
                }
            }

            val response = surveyApi.getSurveys(page = page, pageSize = RemoteApiPaging.PAGE_SIZE)
            val surveys = response.data
            val endOfPaginationReached = surveys.isEmpty()

            // Clear local data for REFRESH
            if (loadType == LoadType.REFRESH) {
                surveyDao.clearAll()
                surveyRemoteKeyDao.clearRemoteKeys()
            }

            val prevKey = if (page == RemoteApiPaging.FIRST_PAGE) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1

            val surveyEntities = surveys.map { surveyDto ->
                surveyEntityMapper.mapToSurveyEntity(surveyDto.data)
            }

            // Insert new surveys and remote keys
            surveyEntities.let { surveyDao.insertAll(it) }
            surveys.map {
                SurveyRemoteKeyEntity(prevPage = prevKey, nextPage = nextKey)
            }.let { surveyRemoteKeyDao.insertAll(it) }

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: IOException) {
            // Return success if local data is available for REFRESH and network fails
            return if (loadType == LoadType.REFRESH && surveyDao.getSurveysCount() > 0) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Error(e)
            }
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        } catch (e: RuntimeException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SurveyEntity>): SurveyRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { surveyRemoteKeyDao.remoteKeysId(it.id) }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SurveyEntity>): SurveyRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { surveyRemoteKeyDao.remoteKeysId(it.id) }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SurveyEntity>
    ): SurveyRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { surveyRemoteKeyDao.remoteKeysId(it) }
        }
    }
}