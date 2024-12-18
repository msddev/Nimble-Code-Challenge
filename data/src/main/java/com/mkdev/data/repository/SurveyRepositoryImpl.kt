package com.mkdev.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.mkdev.data.datasource.local.database.room.dao.SurveyDao
import com.mkdev.data.datasource.local.database.room.dao.SurveyRemoteKeyDao
import com.mkdev.data.datasource.local.mapper.SurveyEntityMapper
import com.mkdev.data.datasource.mediator.SurveyRemoteMediator
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.datasource.remote.mapper.SurveyDomainMapper
import com.mkdev.data.utils.RemoteApiPaging
import com.mkdev.domain.model.survey.SurveyModel
import com.mkdev.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class SurveyRepositoryImpl @Inject constructor(
    private val surveyApi: SurveyApi,
    private val surveyDao: SurveyDao,
    private val surveyRemoteKeyDao: SurveyRemoteKeyDao,
    private val surveyDomainMapper: SurveyDomainMapper,
    private val surveyEntityMapper: SurveyEntityMapper,
) : SurveyRepository {

    override fun getSurveys(): Flow<PagingData<SurveyModel>> {
        val pagingSourceFactory = { surveyDao.getByPaging() }

        return Pager(
            config = PagingConfig(
                pageSize = RemoteApiPaging.PAGE_SIZE,
                maxSize = RemoteApiPaging.PAGE_SIZE + (RemoteApiPaging.PAGE_SIZE * 2),
                enablePlaceholders = false
            ),
            remoteMediator = SurveyRemoteMediator(
                surveyApi = surveyApi,
                surveyDao = surveyDao,
                surveyRemoteKeyDao = surveyRemoteKeyDao,
                surveyEntityMapper = surveyEntityMapper,
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow.map { pagingData ->
            pagingData.map { item ->
                surveyDomainMapper.mapToSurveyDomainModel(item)
            }
        }
    }
}