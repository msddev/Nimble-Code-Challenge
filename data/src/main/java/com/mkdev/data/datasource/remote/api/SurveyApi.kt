package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponse
import com.mkdev.data.datasource.remote.model.response.base.BaseApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SurveyApi {
    @GET("surveys")
    suspend fun getSurveys(
        @Query("page[number]") page: Int,
        @Query("page[size]") pageSize: Int
    ): Response<BaseApiResponse<List<SurveyResponse>>>
}