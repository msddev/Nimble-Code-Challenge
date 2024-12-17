package com.mkdev.data.datasource.local.mapper

import com.mkdev.data.factory.SurveyResponseFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class SurveyEntityMapperTest {

    private val surveyEntityMapper = SurveyEntityMapper()

    @Test
    fun `mapToSurveyEntity should map SurveyResponse to SurveyEntity correctly`() {
        // Given
        val surveyResponse = SurveyResponseFactory.createSurveyResponse()

        // When
        val result = surveyEntityMapper.mapToSurveyEntity(surveyResponse)

        // Then
        assertEquals(surveyResponse.id, result.id)
        assertEquals(surveyResponse.attributes.title, result.title)
        assertEquals(surveyResponse.attributes.description, result.description)
        assertEquals(surveyResponse.attributes.coverImageUrl, result.coverImageUrl)
        assertEquals(surveyResponse.attributes.isActive, result.isActive)
        assertEquals(surveyResponse.attributes.surveyType, result.surveyType)
    }
}