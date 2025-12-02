package com.example.twitchstreamer.presentation.stream.engine

import com.example.twitchstreamer.domain.stream.StreamingEnginePort
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StreamingEngineModule {

    @Binds
    @Singleton
    abstract fun bindStreamingEnginePort(impl: RootEncoderStreamingEngine): StreamingEnginePort
}