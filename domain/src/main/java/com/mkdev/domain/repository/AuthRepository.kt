package com.mkdev.domain.repository

import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(
        grantType: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>>
}