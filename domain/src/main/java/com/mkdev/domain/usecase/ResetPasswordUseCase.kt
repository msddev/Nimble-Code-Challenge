package com.mkdev.domain.usecase

import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

class ResetPasswordUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(
        email: String,
    ): Flow<Resource<String>> {
        return authRepository.resetPassword(email = email)
    }
}