package com.example.twitchstreamer.data.config.di

import com.example.twitchstreamer.data.config.ConfigLocalDataSourceImpl
import com.example.twitchstreamer.data.config.ConfigRepositoryImpl
import com.example.twitchstreamer.data.config.GetStreamKeyUseCaseImpl
import com.example.twitchstreamer.data.config.SaveStreamKeyUseCaseImpl
import com.example.twitchstreamer.domain.config.ConfigLocalDataSource
import com.example.twitchstreamer.domain.config.ConfigRepository
import com.example.twitchstreamer.domain.config.GetStreamKeyUseCase
import com.example.twitchstreamer.domain.config.SaveStreamKeyUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ConfigBindsModule {

    @Binds
    @Singleton
    abstract fun bindConfigLocalDataSource(source: ConfigLocalDataSourceImpl): ConfigLocalDataSource

    @Binds
    @Singleton
    abstract fun bindConfigRepository(repo: ConfigRepositoryImpl): ConfigRepository

    @Binds
    @Singleton
    abstract fun bindGetStreamKeyUseCase(useCase: GetStreamKeyUseCaseImpl): GetStreamKeyUseCase

    @Binds
    @Singleton
    abstract fun bindSaveStreamKeyUseCase(useCase: SaveStreamKeyUseCaseImpl): SaveStreamKeyUseCase
}