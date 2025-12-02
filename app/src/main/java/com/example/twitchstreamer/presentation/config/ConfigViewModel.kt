package com.example.twitchstreamer.presentation.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchstreamer.domain.config.GetStreamKeyUseCase
import com.example.twitchstreamer.domain.config.SaveStreamKeyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ConfigUiState(
    val streamKey: String = "",
    val isSaveEnabled: Boolean = false,
    val isLoading: Boolean = false
)

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val getStreamKey: GetStreamKeyUseCase,
    private val saveStreamKey: SaveStreamKeyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ConfigUiState())
    val state: StateFlow<ConfigUiState> = _state.asStateFlow()

    private val _errorType = MutableSharedFlow<ErrorType>()
    val errorType: SharedFlow<ErrorType> = _errorType

    private val _navigateToMain = MutableSharedFlow<Unit>()
    val navigateToMain: SharedFlow<Unit> = _navigateToMain

    init {
        loadInitialKey()
    }

    private fun loadInitialKey() {
        viewModelScope.launch {
            val saved = getStreamKey().firstOrNull()

            if (!saved.isNullOrBlank()) {
                _state.update {
                    it.copy(
                        streamKey = saved,
                        isSaveEnabled = true
                    )
                }
            }
        }
    }

    fun onStreamKeyChanged(value: String) {
        _state.update {
            it.copy(
                streamKey = value,
                isSaveEnabled = value.isNotBlank()
            )
        }
    }

    fun onSaveClicked() {
        val key = state.value.streamKey.trim()
        if (key.isBlank()) {
            viewModelScope.launch { _errorType.emit(ErrorType.EMPTY_INPUT) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            runCatching {
                saveStreamKey(key)
            }.onSuccess {
                _navigateToMain.emit(Unit)
            }.onFailure {
                _errorType.emit(ErrorType.OTHER)
            }

            _state.update { it.copy(isLoading = false) }
        }
    }
}