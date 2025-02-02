package com.mkdev.domain.model.survey

data class SurveyModel(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val isActive: Boolean,
    val surveyType: String,
)
