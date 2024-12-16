package com.mkdev.domain.usecase

import androidx.paging.PagingData
import com.mkdev.domain.entity.survey.SurveyEntity
import com.mkdev.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow

class GetSurveysUseCase(
    private val surveyRepository: SurveyRepository,
) {
    operator fun invoke(): Flow<PagingData<SurveyEntity>> {
        return surveyRepository.getSurveys()
    }
}