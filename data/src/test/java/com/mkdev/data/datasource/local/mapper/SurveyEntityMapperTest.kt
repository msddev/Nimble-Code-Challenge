package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.factory.SurveyResponseFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class SurveyEntityMapperTest {

    private val surveyEntityMapper = SurveyEntityMapper()

    @Test
    fun `mapToSurveyEntity should map SurveyResponse to SurveyEntity correctly`() {
        // Given
        val surveyResponse = SurveyResponseFactory.createSurveyResponse().data

        // When
        val result = surveyEntityMapper.mapToSurveyEntity(surveyResponse)

        // Then
        assertEquals(surveyResponse.title, result.title)
        assertEquals(surveyResponse.description, result.description)
        assertEquals(surveyResponse.coverImageUrl, result.coverImageUrl)
        assertEquals(surveyResponse.isActive, result.isActive)
        assertEquals(surveyResponse.surveyType, result.surveyType)
    }
}