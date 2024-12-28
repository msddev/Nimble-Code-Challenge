package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.domain.model.survey.SurveyModel
import javax.inject.Inject

class SurveyDomainMapper @Inject constructor() {
    fun mapToSurveyDomainModel(surveyResponse: SurveyResponse): SurveyModel {
        return SurveyModel(
            title = surveyResponse.title,
            description = surveyResponse.description,
            coverImageUrl = surveyResponse.coverImageUrl,
            isActive = surveyResponse.isActive,
            surveyType = surveyResponse.surveyType
        )
    }

    fun mapToSurveyDomainModel(surveyEntity: SurveyEntity): SurveyModel {
        return SurveyModel(
            title = surveyEntity.title,
            description = surveyEntity.description,
            coverImageUrl = surveyEntity.coverImageUrl,
            isActive = surveyEntity.isActive,
            surveyType = surveyEntity.surveyType
        )
    }
}