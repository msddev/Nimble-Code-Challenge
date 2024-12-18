package com.mkdev.data.factory

import com.mkdev.domain.utils.Resource

class ResourceFactory {
    companion object {
        fun <T> loading(): Resource<T> = Resource.Loading()
        fun <T> success(data: T?): Resource<T> = Resource.Success(data)
        fun <T> error(message: String?, data: T? = null): Resource<T> =
            Resource.Error(message, data)
    }
}