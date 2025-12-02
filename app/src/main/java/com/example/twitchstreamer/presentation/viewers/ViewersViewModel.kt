package com.example.twitchstreamer.presentation.viewers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.twitchstreamer.domain.viewers.GetLocalViewersUseCase
import com.example.twitchstreamer.domain.viewers.GetRemoteViewersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ViewerUiState(
    val isLoading: Boolean = false,
    val viewers: List<String> = emptyList()
)

@HiltViewModel
class ViewersViewModel @Inject constructor(
    private val getLocalViewersUseCase: GetLocalViewersUseCase,
    private val getRemoteViewersUseCase: GetRemoteViewersUseCase
) : ViewModel() {

    private val isLoading = MutableStateFlow(false)
    private val inputValue = MutableStateFlow("")
    private val viewers = MutableStateFlow(emptyList<String>())

    val uiState: StateFlow<ViewerUiState> = combine(
        isLoading,
        inputValue,
        viewers,
    ) { isLoading, inputValue, viewers ->
        ViewerUiState(
            isLoading = isLoading,
            viewers = viewers.filter { it.lowercase().contains(inputValue) },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = ViewerUiState()
    )

    private val _oneShotError = MutableSharedFlow<String?>()
    val oneShotError: SharedFlow<String?> = _oneShotError

    init {
        observeLocalViewers()
        refreshFromRemote()
    }

    private fun observeLocalViewers() = viewModelScope.launch {
        getLocalViewersUseCase()
            .collectLatest { list ->
                viewers.emit(
                    list.map { user ->
                        "${user.title} ${user.first} ${user.last}"
                    }
                )
            }
    }

    private fun refreshFromRemote() = viewModelScope.launch {
        isLoading.emit(true)
        try {
            getRemoteViewersUseCase()
        } catch (e: Throwable) {
            _oneShotError.emit(e.message)
        } finally {
            isLoading.emit(false)
        }
    }

    fun onFilterChanged(value: String) {
        inputValue.value = value.lowercase()
    }
}