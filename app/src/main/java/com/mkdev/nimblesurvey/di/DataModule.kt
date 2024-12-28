package com.mkdev.nimblesurvey.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import com.infinum.jsonapix.TypeAdapterFactory
import com.infinum.jsonapix.retrofit.JsonXConverterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
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
import com.mkdev.nimblesurvey.BuildConfig
import com.mkdev.nimblesurvey.utils.ApiConfigs
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
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
        fun provideRetrofitApiService(
            okHttpClient: OkHttpClient,
        ): Retrofit {
            val networkJson = Json {
                ignoreUnknownKeys = true
                isLenient = true
            }

            return Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(JsonXConverterFactory(TypeAdapterFactory()))
                .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
                .client(okHttpClient)
                .build()
        }

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