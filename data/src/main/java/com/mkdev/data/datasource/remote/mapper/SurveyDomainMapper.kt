package com.mkdev.data.datasource.remote.mapper

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.domain.entity.survey.SurveyModel
import javax.inject.Inject

class SurveyDomainMapper @Inject constructor() {
    fun mapToSurveyDomainModel(surveyResponse: SurveyResponse): SurveyModel {
        return SurveyModel(
            id = surveyResponse.id,
            title = surveyResponse.attributes.title,
            description = surveyResponse.attributes.description,
            coverImageUrl = surveyResponse.attributes.coverImageUrl,
            isActive = surveyResponse.attributes.isActive,
            surveyType = surveyResponse.attributes.surveyType
        )
    }

    fun mapToSurveyDomainModel(surveyEntity: SurveyEntity): SurveyModel {
        return SurveyModel(
            id = surveyEntity.id,
            title = surveyEntity.title,
            description = surveyEntity.description,
            coverImageUrl = surveyEntity.coverImageUrl,
            isActive = surveyEntity.isActive,
            surveyType = surveyEntity.surveyType
        )
    }
}