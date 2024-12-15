package com.mkdev.domain.usecase

import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class SignInUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(
        grantType: String,
        email: String,
        password: String,
    ): Flow<Resource<Unit>> {
        return authRepository.signIn(
            grantType = grantType,
            email = email,
            password = password,
        )
    }
}