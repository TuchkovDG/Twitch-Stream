package com.example.twitchstreamer.data.remote.di

import com.example.twitchstreamer.data.remote.api.ViewersApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val API_BASE_URL = "https://randomuser.me/"

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideViewersApi(retrofit: Retrofit): ViewersApi = retrofit.create(ViewersApi::class.java)
}