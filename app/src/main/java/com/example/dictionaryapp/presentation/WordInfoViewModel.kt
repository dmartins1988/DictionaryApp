package com.example.dictionaryapp.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.domain.usecase.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordInfoViewModel @Inject constructor(
    private val getWordInfo: GetWordInfo
): ViewModel() {

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery

    private val _state = MutableStateFlow(WordInfoState())
    val state: StateFlow<WordInfoState> = _state

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var searchJob: Job? = null

    fun onSearch(query: String) {
        _searchQuery.value = query
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500L)
            getWordInfo(query)
                .onEmpty {
                    _state.update { it.copy(
                        wordInfoItems = emptyList(),
                        isLoading = false
                    ) }
                    _eventFlow.emit(UIEvent.ShowSnackBar(
                        "Empty search"
                    ))
                }
                .onEach { result ->
                    when(result) {
                        is Resource.Success -> {
                            _state.update { it.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            ) }
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = false
                            ) }
                            _eventFlow.emit(UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            ))
                        }
                        is Resource.Loading -> {
                            _state.update {it.copy(
                                wordInfoItems = result.data ?: emptyList(),
                                isLoading = true
                            ) }
                        }
                    }
                }.launchIn(this)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String): UIEvent()
    }
}