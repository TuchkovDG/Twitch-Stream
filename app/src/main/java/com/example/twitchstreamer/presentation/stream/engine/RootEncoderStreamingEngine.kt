package com.example.twitchstreamer.presentation.stream.engine

import androidx.annotation.MainThread
import com.example.twitchstreamer.domain.stream.StreamStatus
import com.example.twitchstreamer.domain.stream.StreamingEnginePort
import com.pedro.common.ConnectChecker
import com.pedro.library.rtmp.RtmpCamera2
import com.pedro.library.view.OpenGlView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RootEncoderStreamingEngine @Inject constructor() : StreamingEnginePort, ConnectChecker {

    private var openGlView: OpenGlView? = null
    private var rtmpCamera: RtmpCamera2? = null

    private val _status = MutableStateFlow(StreamStatus.OFFLINE)
    override val status: StateFlow<StreamStatus> = _status.asStateFlow()

    private val _durationSeconds = MutableStateFlow(0L)
    override val durationSeconds: StateFlow<Long> = _durationSeconds.asStateFlow()

    private var durationJob: Job? = null
    private val durationScope = CoroutineScope(Dispatchers.Main.immediate)

    @MainThread
    fun attachPreview(preview: OpenGlView) {
        openGlView = preview
        if (rtmpCamera == null) {
            rtmpCamera = RtmpCamera2(preview, this)
        } else {
            rtmpCamera?.let { camera ->
                camera.stopPreview()
                camera.stopStream()
                rtmpCamera = RtmpCamera2(preview, this)
            }
        }
    }

    @MainThread
    fun detachPreview() {
        stopStreamingInternal()
        stopPreviewInternal()
        openGlView = null
        rtmpCamera = null
    }

    override suspend fun startPreview() = withContext(Dispatchers.Default) {
        startPreviewInternal()
    }

    override suspend fun stopPreview() = withContext(Dispatchers.Default) {
        stopPreviewInternal()
    }

    override suspend fun startStreaming(
        serverUrl: String,
        streamKey: String
    ) = withContext(Dispatchers.Default) {
        startStreamingInternal(serverUrl, streamKey)
    }

    override suspend fun stopStreaming() = withContext(Dispatchers.Default) {
        stopStreamingInternal()
    }

    override suspend fun setMicEnabled(enabled: Boolean) = withContext(Dispatchers.Default) {
        setMicEnabledInternal(enabled)
    }

    override suspend fun switchCamera(): Unit = withContext(Dispatchers.Default) {
        rtmpCamera?.switchCamera()
    }

    private fun startPreviewInternal() {
        val camera = requireCamera()

        if (camera.isOnPreview) return

        val preparedVideo = camera.prepareVideo()
        val preparedAudio = camera.prepareAudio()

        if (!preparedVideo || !preparedAudio) {
            _status.value = StreamStatus.ERROR
            return
        }

        camera.startPreview()
    }

    private fun stopPreviewInternal() {
        val camera = rtmpCamera ?: return
        if (camera.isStreaming) return
        if (camera.isOnPreview) {
            camera.stopPreview()
        }
        stopDurationCounter()
    }

    private fun startStreamingInternal(serverUrl: String, streamKey: String) {
        val camera = requireCamera()

        if (camera.isStreaming) return

        if (!camera.isOnPreview) {
            camera.startPreview()
        }

        val preparedVideo = camera.prepareVideo()
        val preparedAudio = camera.prepareAudio()
        if (!preparedVideo || !preparedAudio) {
            _status.value = StreamStatus.ERROR
            return
        }

        camera.streamClient.setReTries(5)
        camera.startStream("$serverUrl/$streamKey")
        startDurationCounter()
    }

    private fun stopStreamingInternal() {
        rtmpCamera?.takeIf { it.isStreaming }?.stopStream()
        stopDurationCounter()
    }

    private fun setMicEnabledInternal(enabled: Boolean) {
        val camera = rtmpCamera ?: return
        if (enabled && camera.isAudioMuted) {
            camera.enableAudio()
        } else if (!enabled && !camera.isAudioMuted) {
            camera.disableAudio()
        }
    }

    private fun requireCamera(): RtmpCamera2 {
        val preview = openGlView ?: error("attachPreview(OpenGlView) must be called before using StreamingEngine")
        return rtmpCamera ?: RtmpCamera2(preview, this).also { rtmpCamera = it }
    }

    private fun startDurationCounter() {
        if (durationJob != null) return

        durationJob = durationScope.launch {
            var seconds = _durationSeconds.value
            while (isActive) {
                delay(1_000)
                seconds++
                _durationSeconds.value = seconds
            }
        }
    }

    private fun stopDurationCounter() {
        durationJob?.cancel()
        durationJob = null
        _durationSeconds.value = 0L
    }

    override fun onConnectionStarted(url: String) {
        _status.value = StreamStatus.CONNECTING
    }

    override fun onConnectionSuccess() {
        _status.value = StreamStatus.ONLINE
    }

    override fun onConnectionFailed(reason: String) {
        _status.value = StreamStatus.ERROR
        stopStreamingInternal()
    }

    override fun onDisconnect() {
        _status.value = StreamStatus.OFFLINE
        stopStreamingInternal()
    }

    override fun onAuthError() {
        _status.value = StreamStatus.ERROR
        stopStreamingInternal()
    }

    override fun onAuthSuccess() { /* no-op */ }
}