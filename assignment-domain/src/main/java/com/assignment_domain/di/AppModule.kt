package com.assignment_domain.di

import com.assignment_core.util.Constants
import com.assignment_domain.repository.GameRepository
import com.assignment_domain.repository.GameRepositoryImpl
import com.data.service.GameApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): Retrofit = Retrofit.Builder().client(
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    ).addConverterFactory(GsonConverterFactory.create()).baseUrl(Constants.BASE_URL).build()

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): GameApi = retrofit.create(GameApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi): GameRepository {
        return GameRepositoryImpl(gameApi)
    }
}