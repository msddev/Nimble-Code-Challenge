package com.mkdev.data.datasource.local.database.room.mediator

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
            val response = surveyApi.getSurveys(
                page = page,
                pageSize = RemoteApiPaging.PAGE_SIZE
            )
            val surveys = response.body()?.data
            val endOfPaginationReached = response.isSuccessful.not() && response.code() == 404

            if (loadType == LoadType.REFRESH) {
                surveyRemoteKeyDao.clearRemoteKeys()
                surveyDao.clearAll()
            }
            val prevPage = if (page == RemoteApiPaging.FIRST_PAGE) null else page - 1
            val nextPage = if (endOfPaginationReached) null else page + 1
            surveys?.map {
                SurveyRemoteKeyEntity(surveyId = it.id, prevPage = prevPage, nextPage = nextPage)
            }?.also { keys ->
                surveyRemoteKeyDao.insertAll(keys)
            }


            surveys?.map { surveyDto ->
                surveyEntityMapper.mapToSurveyEntity(surveyDto)
            }?.also { surveyEntities ->
                surveyDao.insertAll(surveyEntities)
            }


            return MediatorResult.Success(endOfPaginationReached = false)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, SurveyEntity>): SurveyRemoteKeyEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { repo ->
                surveyRemoteKeyDao.remoteKeysId(repo.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, SurveyEntity>): SurveyRemoteKeyEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { item ->
                surveyRemoteKeyDao.remoteKeysId(item.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, SurveyEntity>
    ): SurveyRemoteKeyEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                surveyRemoteKeyDao.remoteKeysId(id)
            }
        }
    }
}