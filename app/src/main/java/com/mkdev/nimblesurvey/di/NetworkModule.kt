package com.mkdev.nimblesurvey.di

import android.content.Context
import com.mkdev.data.datasource.remote.api.AuthApi
import com.mkdev.nimblesurvey.BuildConfig
import com.mkdev.nimblesurvey.utils.ApiConfigs
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
class NetworkModule {

    @Provides
    @Singleton
    fun provideCache(@ApplicationContext context: Context) =
        okhttp3.Cache(context.cacheDir, ApiConfigs.CACHE_SIZE)

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: okhttp3.Cache): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
                addInterceptor(loggingInterceptor)
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
            .baseUrl(ApiConfigs.BASE_URL)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideAuthApiService(
        retrofit: Retrofit,
    ): AuthApi = retrofit.create(AuthApi::class.java)
}