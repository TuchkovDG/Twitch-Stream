package com.example.twitchstreamer.domain.stream

import kotlinx.coroutines.flow.StateFlow

/**
 * Interface to provide a stream status in [StreamStatus] instance.
 */
interface GetStreamStatusUseCase {

    operator fun invoke(): StateFlow<StreamStatus>
}

/**
 * Interface to provide a stream duration in seconds.
 */
interface GetStreamDurationSecondsUseCase {

    operator fun invoke(): StateFlow<Long>
}


/**
 * An use case to start video preview.
 */
interface StartPreviewUseCase {

    suspend operator fun invoke()
}

/**
 * An use case to stop video preview.
 */
interface StopPreviewUseCase {

    suspend operator fun invoke()
}

/**
 * An use case to start stream.
 */
interface StartStreamingUseCase {

    suspend operator fun invoke()
}

/**
 * An use case to stop stream.
 */
interface StopStreamingUseCase {

    suspend operator fun invoke()
}

/**
 * An use case to set microphone enabled or disabled.
 */
interface SetMicEnabledUseCase {

    suspend operator fun invoke(enabled: Boolean)
}

/**
 * An use case to switch camera to front or rear.
 */
interface SwitchCameraUseCase {

    suspend operator fun invoke()
}