package com.mkdev.data.datasource.remote.api

import com.mkdev.data.datasource.remote.model.response.survey.SurveyResponseList
import retrofit2.http.GET
import retrofit2.http.Query

interface SurveyApi {
    @GET("surveys")
    suspend fun getSurveys(
        @Query("page[number]") page: Int,
        @Query("page[size]") pageSize: Int
    ): SurveyResponseList
}