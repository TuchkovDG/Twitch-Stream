package com.example.twitchstreamer.data.viewers.di

import com.example.twitchstreamer.data.viewers.GetLocalViewersUseCaseCaseImpl
import com.example.twitchstreamer.data.viewers.GetRemoteViewersUseCaseImpl
import com.example.twitchstreamer.data.viewers.ViewersLocalDataSourceImpl
import com.example.twitchstreamer.data.viewers.ViewersRemoteDataSourceImpl
import com.example.twitchstreamer.data.viewers.ViewersRepositoryImpl
import com.example.twitchstreamer.domain.viewers.GetLocalViewersUseCase
import com.example.twitchstreamer.domain.viewers.GetRemoteViewersUseCase
import com.example.twitchstreamer.domain.viewers.ViewersLocalDataSource
import com.example.twitchstreamer.domain.viewers.ViewersRemoteDataSource
import com.example.twitchstreamer.domain.viewers.ViewersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ViewersBindsModule {

    @Binds
    @Singleton
    abstract fun bindViewersRemoteDataSource(source: ViewersRemoteDataSourceImpl): ViewersRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindViewersLocalDataSource(source: ViewersLocalDataSourceImpl): ViewersLocalDataSource

    @Binds
    @Singleton
    abstract fun bindViewersRepository(repo: ViewersRepositoryImpl): ViewersRepository

    @Binds
    @Singleton
    abstract fun bindGetLocalViewersUseCase(useCase: GetLocalViewersUseCaseCaseImpl): GetLocalViewersUseCase

    @Binds
    @Singleton
    abstract fun bindGetRemoteViewersUseCase(useCase: GetRemoteViewersUseCaseImpl): GetRemoteViewersUseCase
}