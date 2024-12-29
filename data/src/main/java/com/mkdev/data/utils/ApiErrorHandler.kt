package com.mkdev.data.utils

import com.google.gson.JsonSyntaxException
import com.infinum.jsonapix.asJsonXHttpException
import com.infinum.jsonapix.core.resources.DefaultError
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ApiErrorHandler @Inject constructor() {
    fun handleError(throwable: Throwable): ApiException {
        return when (throwable) {
            is IOException -> ApiException.NetworkError(throwable.message)
            is HttpException -> {
                val code = throwable.code()
                try {
                    val jsonXHttpException = throwable.asJsonXHttpException<DefaultError>()
                    val defaultError = jsonXHttpException.errors?.firstOrNull()
                    ApiException.HttpError(code, defaultError?.detail)
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