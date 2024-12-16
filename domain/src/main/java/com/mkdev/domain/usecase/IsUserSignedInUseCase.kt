package com.mkdev.domain.usecase

import com.mkdev.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class IsUserSignedInUseCase(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(): Flow<Boolean> {
        return authRepository.isUserSignedIn()
    }
}