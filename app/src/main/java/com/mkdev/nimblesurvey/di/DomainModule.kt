package com.mkdev.nimblesurvey.di

import com.mkdev.data.datasource.local.database.room.dao.SurveyDao
import com.mkdev.data.datasource.local.database.room.dao.SurveyRemoteKeyDao
import com.mkdev.data.datasource.local.datastore.UserLocalSource
import com.mkdev.data.datasource.local.mapper.SignInMapper
import com.mkdev.data.datasource.local.mapper.SurveyEntityMapper
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.datasource.remote.mapper.SurveyDomainMapper
import com.mkdev.data.repository.AuthRepositoryImpl
import com.mkdev.data.repository.SurveyRepositoryImpl
import com.mkdev.data.utils.ApiErrorHandler
import com.mkdev.data.utils.ClientKeysNdkWrapper
import com.mkdev.domain.repository.AuthRepository
import com.mkdev.domain.repository.SurveyRepository
import com.mkdev.domain.usecase.GetSurveysUseCase
import com.mkdev.domain.usecase.IsUserSignedInUseCase
import com.mkdev.domain.usecase.ResetPasswordUseCase
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
    fun provideIsUserSignInUseCase(repository: AuthRepository): IsUserSignedInUseCase =
        IsUserSignedInUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideGetSurveysUseCase(repository: SurveyRepository): GetSurveysUseCase =
        GetSurveysUseCase(surveyRepository = repository)

    @Provides
    @Singleton
    fun provideResetPasswordUseCase(repository: AuthRepository): ResetPasswordUseCase =
        ResetPasswordUseCase(authRepository = repository)

    @Provides
    @Singleton
    fun provideAuthRepository(
        userLocalSource: UserLocalSource,
        authApi: AuthApi,
        apiErrorHandler: ApiErrorHandler,
        signInMapper: SignInMapper,
        clientKeysNdkWrapper: ClientKeysNdkWrapper
    ): AuthRepository =
        AuthRepositoryImpl(
            userLocalSource = userLocalSource,
            authApi = authApi,
            apiErrorHandler = apiErrorHandler,
            signInMapper = signInMapper,
            clientKeysNdkWrapper = clientKeysNdkWrapper
        )

    @Provides
    @Singleton
    fun provideSurveyRepository(
        surveyApi: SurveyApi,
        surveyDao: SurveyDao,
        surveyRemoteKeyDao: SurveyRemoteKeyDao,
        surveyDomainMapper: SurveyDomainMapper,
        surveyEntityMapper: SurveyEntityMapper,
    ): SurveyRepository =
        SurveyRepositoryImpl(
            surveyApi = surveyApi,
            surveyDao = surveyDao,
            surveyRemoteKeyDao = surveyRemoteKeyDao,
            surveyDomainMapper = surveyDomainMapper,
            surveyEntityMapper = surveyEntityMapper,
        )
}