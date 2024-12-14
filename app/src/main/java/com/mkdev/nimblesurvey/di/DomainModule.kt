package com.mkdev.nimblesurvey.di

import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.repository.AuthRepositoryImpl
import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideSignInUseCase(repository: AuthRepository): SignInUseCase =
        SignInUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideAuthRepository(authApi: AuthApi): AuthRepository =
        AuthRepositoryImpl(authApi = authApi)
}