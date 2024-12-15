package com.mkdev.data.utils

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.mkdev.data.datasource.remote.model.response.error.LoginErrorResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor(private val gson: Gson) {
    fun handleError(throwable: Throwable): ApiException {
        return when (throwable) {
            is IOException -> ApiException.NetworkError(throwable.message)
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val code = throwable.code()
                try {
                    val errorResponse = gson.fromJson(errorBody, LoginErrorResponse::class.java)
                    val errorMessage = errorResponse.errors.firstOrNull()?.detail
                    ApiException.HttpError(code, errorMessage)
                } catch (exception: JsonSyntaxException) {
                    ApiException.ParseError("Error parsing error response")
                } catch (exception: Exception) {
                    ApiException.HttpError(code, "Unknown HTTP error")
                }
            }

            else -> ApiException.UnknownError(throwable.message)
        }
    }
}

sealed class ApiException(
    override val message: String? = null,
    val code: Int? = null,
) : Exception(message) {
    class NetworkError(message: String? = null) : ApiException(message)
    class HttpError(code: Int, message: String? = null) : ApiException(message, code)
    class UnknownError(message: String? = null) : ApiException(message)
    class ServerError(message: String? = null) : ApiException(message)
    class ParseError(message: String? = null) : ApiException(message)
}