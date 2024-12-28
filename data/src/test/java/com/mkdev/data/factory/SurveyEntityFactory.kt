package com.mkdev.data.factory

import com.mkdev.data.datasource.local.database.room.entity.SurveyEntity

object SurveyEntityFactory {

    fun createSurveyEntity(
        title: String = "Survey Title",
        description: String = "Survey Description",
        coverImageUrl: String = "https://example.com/image.jpg",
        isActive: Boolean = true,
        surveyType: String = "customer_satisfaction"
    ): SurveyEntity {
        return SurveyEntity(
            title = title,
            description = description,
            coverImageUrl = coverImageUrl,
            isActive = isActive,
            surveyType = surveyType
        )
    }
}