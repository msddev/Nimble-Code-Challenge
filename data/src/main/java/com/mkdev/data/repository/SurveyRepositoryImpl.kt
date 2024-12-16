package com.mkdev.data.repository

import androidx.paging.PagingData
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.datasource.remote.mapper.SurveyMapper
import com.mkdev.domain.entity.survey.SurveyEntity
import com.mkdev.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val surveyApi: SurveyApi,
    private val surveyMapper: SurveyMapper,
) : SurveyRepository {

    override fun getSurveys(): Flow<PagingData<SurveyEntity>> {
        /*return Pager(
            config = androidx.paging.PagingConfig(pageSize = 5),
            pagingSourceFactory = { SurveyPagingSource(surveyApi, surveyMapper) }
        ).flow*/

        TODO()
    }
}