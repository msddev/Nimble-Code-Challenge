package com.mkdev.domain.repository

import androidx.paging.PagingData
import com.mkdev.domain.entity.survey.SurveyModel
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun getSurveys(): Flow<PagingData<SurveyModel>>
}