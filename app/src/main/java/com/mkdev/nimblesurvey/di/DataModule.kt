package com.mkdev.nimblesurvey.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.google.gson.Gson
import com.mkdev.data.datasource.local.UserLocal
import com.mkdev.data.datasource.local.crypto.Crypto
import com.mkdev.data.datasource.local.crypto.CryptoImpl
import com.mkdev.data.datasource.local.database.room.NimbleRoomDatabase
import com.mkdev.data.datasource.local.datastore.DataStoreHolder
import com.mkdev.data.datasource.local.datastore.UserLocalSerializer
import com.mkdev.data.datasource.local.datastore.UserLocalSource
import com.mkdev.data.datasource.local.datastore.UserLocalSourceImpl
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.data.datasource.remote.api.SurveyApi
import com.mkdev.data.datasource.remote.interceptor.AuthInterceptor
import com.mkdev.data.utils.ApiErrorHandler
import com.mkdev.nimblesurvey.BuildConfig
import com.mkdev.nimblesurvey.utils.ApiConfigs
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import kotlin.time.toJavaDuration

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun provideCrypto(cryptoImpl: CryptoImpl): Crypto

    @Binds
    @Singleton
    abstract fun provideUserLocalSerializer(
        userLocalSerializerImpl: UserLocalSerializer
    ): Serializer<UserLocal>

    @Binds
    @Singleton
    abstract fun provideUserLocalSource(
        userLocalSourceImpl: UserLocalSourceImpl
    ): UserLocalSource

    internal companion object {

        @Provides
        @Singleton
        fun provideApiErrorHandler(gson: Gson): ApiErrorHandler {
            return ApiErrorHandler(gson)
        }

        @Provides
        @Singleton
        fun provideGson(): Gson = Gson()

        @Provides
        @Singleton
        fun provideDataStore(
            @ApplicationContext applicationContext: Context,
            serializer: Serializer<UserLocal>
        ): DataStore<UserLocal> {
            return DataStoreHolder.getInstance(applicationContext, serializer)
        }

        @Provides
        @Singleton
        fun provideCache(@ApplicationContext context: Context) =
            okhttp3.Cache(context.cacheDir, ApiConfigs.CACHE_SIZE)

        @Provides
        @Singleton
        fun provideOkHttpClient(
            cache: okhttp3.Cache,
            authInterceptor: AuthInterceptor,
        ): OkHttpClient = OkHttpClient.Builder()
            .apply {
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                    addInterceptor(loggingInterceptor)
                    addInterceptor(authInterceptor)
                }
            }
            .cache(cache)
            .connectTimeout(ApiConfigs.Timeouts.connect.toJavaDuration())
            .writeTimeout(ApiConfigs.Timeouts.write.toJavaDuration())
            .readTimeout(ApiConfigs.Timeouts.read.toJavaDuration())
            .build()

        @Singleton
        @Provides
        fun provideConverterFactory(): Converter.Factory {
            return GsonConverterFactory.create()
        }

        @Singleton
        @Provides
        fun provideRetrofitApiService(
            okHttpClient: OkHttpClient,
            converterFactory: Converter.Factory,
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build()

        @Singleton
        @Provides
        fun provideAuthApiService(
            retrofit: Retrofit,
        ): AuthApi = retrofit.create(AuthApi::class.java)

        @Singleton
        @Provides
        fun provideSurveyApiService(
            retrofit: Retrofit,
        ): SurveyApi = retrofit.create(SurveyApi::class.java)

        @Singleton
        @Provides
        fun provideDatabase(@ApplicationContext context: Context) =
            NimbleRoomDatabase.getDatabase(context)

        @Singleton
        @Provides
        fun provideSurveyDao(database: NimbleRoomDatabase) =
            database.surveyDao()

        @Singleton
        @Provides
        fun provideSurveyRemoteKeyDao(database: NimbleRoomDatabase) =
            database.surveyRemoteKeyDao()
    }
}