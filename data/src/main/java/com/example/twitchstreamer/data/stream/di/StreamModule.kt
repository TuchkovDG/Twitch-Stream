package com.example.twitchstreamer.data.stream.di

import com.example.twitchstreamer.data.stream.GetStreamDurationSecondsUseCaseImpl
import com.example.twitchstreamer.data.stream.GetStreamStatusUseCaseImpl
import com.example.twitchstreamer.data.stream.SetMicEnabledUseCaseImpl
import com.example.twitchstreamer.data.stream.StartPreviewUseCaseImpl
import com.example.twitchstreamer.data.stream.StartStreamingUseCaseImpl
import com.example.twitchstreamer.data.stream.StopPreviewUseCaseImpl
import com.example.twitchstreamer.data.stream.StopStreamingUseCaseImpl
import com.example.twitchstreamer.data.stream.StreamRepositoryImpl
import com.example.twitchstreamer.data.stream.SwitchCameraUseCaseImpl
import com.example.twitchstreamer.domain.stream.GetStreamDurationSecondsUseCase
import com.example.twitchstreamer.domain.stream.GetStreamStatusUseCase
import com.example.twitchstreamer.domain.stream.SetMicEnabledUseCase
import com.example.twitchstreamer.domain.stream.StartPreviewUseCase
import com.example.twitchstreamer.domain.stream.StartStreamingUseCase
import com.example.twitchstreamer.domain.stream.StopPreviewUseCase
import com.example.twitchstreamer.domain.stream.StopStreamingUseCase
import com.example.twitchstreamer.domain.stream.StreamRepository
import com.example.twitchstreamer.domain.stream.SwitchCameraUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class StreamBindsModule {

    @Binds
    @Singleton
    abstract fun bindStreamRepository(repo: StreamRepositoryImpl): StreamRepository

    @Binds
    @Singleton
    abstract fun bindGetStreamStatusUseCase(useCase: GetStreamStatusUseCaseImpl): GetStreamStatusUseCase

    @Binds
    @Singleton
    abstract fun bindGetStreamDurationSecondsUseCase(useCase: GetStreamDurationSecondsUseCaseImpl): GetStreamDurationSecondsUseCase

    @Binds
    @Singleton
    abstract fun bindStartPreviewUseCase(useCase: StartPreviewUseCaseImpl): StartPreviewUseCase

    @Binds
    @Singleton
    abstract fun bindStopPreviewUseCase(useCase: StopPreviewUseCaseImpl): StopPreviewUseCase

    @Binds
    @Singleton
    abstract fun bindStartStreamingUseCase(useCase: StartStreamingUseCaseImpl): StartStreamingUseCase

    @Binds
    @Singleton
    abstract fun bindStopStreamingUseCase(useCase: StopStreamingUseCaseImpl): StopStreamingUseCase

    @Binds
    @Singleton
    abstract fun bindSetMicEnabledUseCase(useCase: SetMicEnabledUseCaseImpl): SetMicEnabledUseCase

    @Binds
    @Singleton
    abstract fun bindSwitchCameraUseCase(useCase: SwitchCameraUseCaseImpl): SwitchCameraUseCase
}