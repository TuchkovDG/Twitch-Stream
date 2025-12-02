package com.example.twitchstreamer.presentation.stream

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchstreamer.domain.stream.GetStreamDurationSecondsUseCase
import com.example.twitchstreamer.domain.stream.GetStreamStatusUseCase
import com.example.twitchstreamer.domain.stream.SetMicEnabledUseCase
import com.example.twitchstreamer.domain.stream.StartPreviewUseCase
import com.example.twitchstreamer.domain.stream.StartStreamingUseCase
import com.example.twitchstreamer.domain.stream.StopStreamingUseCase
import com.example.twitchstreamer.domain.stream.StreamStatus
import com.example.twitchstreamer.domain.stream.SwitchCameraUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class StreamUiState(
    val isPreviewActive: Boolean = false,
    val status: StreamStatus = StreamStatus.OFFLINE,
    val formattedDuration: String = "",
    val isMicEnabled: Boolean = true
)

@HiltViewModel
class StreamViewModel @Inject constructor(
    getStreamStatusUseCase: GetStreamStatusUseCase,
    getStreamDurationSecondsUseCase: GetStreamDurationSecondsUseCase,
    private val startPreviewUseCase: StartPreviewUseCase,
    private val startStreamingUseCase: StartStreamingUseCase,
    private val stopStreamingUseCase: StopStreamingUseCase,
    private val setMicEnabledUseCase: SetMicEnabledUseCase,
    private val switchCameraUseCase: SwitchCameraUseCase
) : ViewModel() {

    private val _isPreviewActive = MutableStateFlow(false)
    private val _isMicEnabled = MutableStateFlow(true)

    val uiState: StateFlow<StreamUiState> = combine(
        _isPreviewActive,
        getStreamStatusUseCase(),
        getStreamDurationSecondsUseCase(),
        _isMicEnabled
    ) { isPreviewActive, status, duration, isMicEnabled ->
        StreamUiState(
            isPreviewActive = isPreviewActive,
            status = status,
            formattedDuration = formatDuration(duration),
            isMicEnabled = isMicEnabled
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = StreamUiState()
    )

    private val _navigateToViewers = MutableSharedFlow<Unit>()
    val navigateToViewers: SharedFlow<Unit> = _navigateToViewers

    @SuppressLint("DefaultLocale")
    private fun formatDuration(seconds: Long): String {
        return if (seconds == 0L) {
            ""
        } else {
            val h = seconds / 3600
            val m = (seconds % 3600) / 60
            val s = seconds % 60

            if (h > 0) {
                String.format("%02d:%02d:%02d", h, m, s)
            } else {
                String.format("%02d:%02d", m, s)
            }
        }
    }

    fun onStartPreview() {
        if (_isPreviewActive.value) return

        viewModelScope.launch {
            startPreviewUseCase()
            _isPreviewActive.emit(true)
        }
    }

    fun onStartStopStreamClicked() {
        viewModelScope.launch {
            if (uiState.value.status == StreamStatus.ONLINE) {
                stopStreamingUseCase()
            } else {
                if (!_isPreviewActive.value) {
                    startPreviewUseCase()
                }
                startStreamingUseCase()
            }
        }
    }

    fun onToggleMicClicked() {
        val newEnabled = !_isMicEnabled.value
        _isMicEnabled.value = newEnabled

        viewModelScope.launch {
            setMicEnabledUseCase(newEnabled)
        }
    }

    fun onSwitchCameraClicked() {
        viewModelScope.launch {
            switchCameraUseCase()
        }
    }

    fun onViewersClicked() {
        viewModelScope.launch {
            _navigateToViewers.emit(Unit)
        }
    }
}