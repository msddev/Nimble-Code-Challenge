package com.mkdev.domain.usecase

import androidx.paging.PagingData
import com.mkdev.domain.model.survey.SurveyModel
import com.mkdev.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow

class GetSurveysUseCase(
    private val surveyRepository: SurveyRepository,
) {
    operator fun invoke(): Flow<PagingData<SurveyModel>> {
        return surveyRepository.getSurveys()
    }
}