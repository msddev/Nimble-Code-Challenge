package com.mkdev.domain.entity.survey

data class SurveyEntity(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val isActive: Boolean,
    val surveyType: String,
)
