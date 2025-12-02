package com.example.twitchstreamer.domain.stream

import kotlinx.coroutines.flow.StateFlow

/**
 * A stream engine port interface that represents stream information and functionality.
 */
interface StreamingEnginePort {

    /**
     * Represents status of stream.
     */
    val status: StateFlow<StreamStatus>
    /**
     * Represents duration of stream in seconds.
     */
    val durationSeconds: StateFlow<Long>

    suspend fun startPreview()
    suspend fun stopPreview()

    suspend fun startStreaming(serverUrl: String, streamKey: String)
    suspend fun stopStreaming()

    suspend fun setMicEnabled(enabled: Boolean)
    suspend fun switchCamera()
}