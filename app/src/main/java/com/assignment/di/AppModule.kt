package com.assignment.di

import com.assignment_domain.repository.GameRepository
import com.data.remote.GameApi
import com.data.repository.GameRepositoryImpl
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
    ).addConverterFactory(GsonConverterFactory.create()).baseUrl(BASE_URL).build()

    @Singleton
    @Provides
    fun provideGameApi(retrofit: Retrofit): GameApi = retrofit.create(GameApi::class.java)

    @Singleton
    @Provides
    fun provideRepository(gameApi: GameApi): GameRepository {
        return GameRepositoryImpl(gameApi)
    }

    companion object {
        private const val BASE_URL: String = "https://www.freetogame.com/api/"
    }
}
