package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.data.factory.MetaResponseFactory
import com.mkdev.data.factory.SurveyResponseFactory
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class SurveyApiTest {

    @MockK
    private lateinit var surveyApi: SurveyApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getSurveys should call correct endpoint with correct query parameters and return success response`() =
        runBlocking {
            // Given
            val page = 1
            val pageSize = 20
            val surveyResponses = SurveyResponseFactory.createSurveyResponseList()
            val metaResponse = MetaResponseFactory.createMetaResponse()
            val expectedResponse =
                Response.success(BaseApiResponse(data = surveyResponses, meta = metaResponse))
            coEvery { surveyApi.getSurveys(page, pageSize) } returns expectedResponse

            // When
            val actualResponse = surveyApi.getSurveys(page, pageSize)

            // Then
            coVerify(exactly = 1) { surveyApi.getSurveys(page, pageSize) }
            assertEquals(expectedResponse, actualResponse)
        }

    @Test
    fun `getSurveys should handle error response`() = runBlocking {
        // Given
        val page = 1
        val pageSize = 20
        val errorResponse =
            Response.error<BaseApiResponse<List<SurveyResponse>>>(400, "".toResponseBody())
        coEvery { surveyApi.getSurveys(page, pageSize) } returns errorResponse

        // When
        val actualResponse = surveyApi.getSurveys(page, pageSize)

        // Then
        coVerify(exactly = 1) { surveyApi.getSurveys(page, pageSize) }
        assertEquals(errorResponse, actualResponse)
    }
}