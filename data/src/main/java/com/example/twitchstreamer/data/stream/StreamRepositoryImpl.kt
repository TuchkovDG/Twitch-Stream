package com.example.twitchstreamer.data.stream

import com.example.twitchstreamer.domain.stream.StreamRepository
import com.example.twitchstreamer.domain.stream.StreamStatus
import com.example.twitchstreamer.domain.stream.StreamingEnginePort
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

private const val STREAM_COMMAND = "rtmp://live.twitch.tv/app"

internal class StreamRepositoryImpl @Inject constructor(
    private val engine: StreamingEnginePort
) : StreamRepository {

    override val status: StateFlow<StreamStatus> = engine.status
    override val durationSeconds: StateFlow<Long> = engine.durationSeconds

    override suspend fun startPreview() {
        engine.startPreview()
    }

    override suspend fun stopPreview() {
        engine.stopPreview()
    }

    override suspend fun startStreaming(streamKey: String) {
        engine.startStreaming(
            serverUrl = STREAM_COMMAND,
            streamKey = streamKey
        )
    }

    override suspend fun stopStreaming() {
        engine.stopStreaming()
    }

    override suspend fun setMicEnabled(enabled: Boolean) {
        engine.setMicEnabled(enabled)
    }

    override suspend fun switchCamera() {
        engine.switchCamera()
    }
}