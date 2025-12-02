package com.example.twitchstreamer.domain.stream

import kotlinx.coroutines.flow.StateFlow

/**
 * An repository interface that represents stream information and functionality.
 */
interface StreamRepository {

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

    suspend fun startStreaming(streamKey: String)
    suspend fun stopStreaming()

    suspend fun setMicEnabled(enabled: Boolean)
    suspend fun switchCamera()
}