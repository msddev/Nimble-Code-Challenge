package com.mkdev.presentation.model.local

internal data class SurveyModel(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val surveyType: String
)