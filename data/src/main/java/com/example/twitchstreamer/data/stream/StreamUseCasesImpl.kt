package com.example.twitchstreamer.data.stream

import com.example.twitchstreamer.domain.config.ConfigRepository
import com.example.twitchstreamer.domain.config.SaveStreamKeyUseCase
import com.example.twitchstreamer.domain.stream.GetStreamDurationSecondsUseCase
import com.example.twitchstreamer.domain.stream.GetStreamStatusUseCase
import com.example.twitchstreamer.domain.stream.SetMicEnabledUseCase
import com.example.twitchstreamer.domain.stream.StartPreviewUseCase
import com.example.twitchstreamer.domain.stream.StartStreamingUseCase
import com.example.twitchstreamer.domain.stream.StopPreviewUseCase
import com.example.twitchstreamer.domain.stream.StopStreamingUseCase
import com.example.twitchstreamer.domain.stream.StreamRepository
import com.example.twitchstreamer.domain.stream.StreamStatus
import com.example.twitchstreamer.domain.stream.SwitchCameraUseCase
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * An implementation of [GetStreamStatusUseCase] that provides the stream status via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class GetStreamStatusUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : GetStreamStatusUseCase {

    override fun invoke(): StateFlow<StreamStatus> = streamRepository.status
}

/**
 * An implementation of [GetStreamStatusUseCase] that provides the stream duration in second
 * via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class GetStreamDurationSecondsUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : GetStreamDurationSecondsUseCase {

    override fun invoke(): StateFlow<Long> = streamRepository.durationSeconds
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to start video preview
 * via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class StartPreviewUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : StartPreviewUseCase {

    override suspend fun invoke() {
        streamRepository.startPreview()
    }
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to stop video preview
 * via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class StopPreviewUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : StopPreviewUseCase {

    override suspend fun invoke() {
        streamRepository.stopPreview()
    }
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to start stream
 * via the [StreamRepository] and [ConfigRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 * @property configRepository A [ConfigRepository] instance.
 */
internal class StartStreamingUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository,
    private val configRepository: ConfigRepository
) : StartStreamingUseCase {

    override suspend fun invoke() {
        streamRepository.startStreaming(streamKey = configRepository.getStreamKey().first())
    }
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to stop stream
 * via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class StopStreamingUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : StopStreamingUseCase {

    override suspend fun invoke() {
        streamRepository.stopStreaming()
    }
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to set microphone enabled
 * or disabled via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class SetMicEnabledUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : SetMicEnabledUseCase {

    override suspend fun invoke(enabled: Boolean) {
        streamRepository.setMicEnabled(enabled)
    }
}

/**
 * An implementation of [SaveStreamKeyUseCase] that provides ability to switch camera to
 * front or rear via the [StreamRepository].
 *
 * @property streamRepository A [StreamRepository] instance.
 */
internal class SwitchCameraUseCaseImpl @Inject constructor(
    private val streamRepository: StreamRepository
) : SwitchCameraUseCase {

    override suspend fun invoke() {
        streamRepository.switchCamera()
    }
}