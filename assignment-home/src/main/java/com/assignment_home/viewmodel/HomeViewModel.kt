package com.assignment_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment_core.state.GameState
import com.assignment_core.state.HomeUiEvent
import com.assignment_core.state.UiEffect
import com.assignment_domain.usecases.HomeApiUseCase
import com.data.service.DataLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeApiUseCase) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> get() = _gameState

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    init {
        getAllGameList()
    }

    private fun getAllGameList() = useCase().onEach { state ->
        when (state) {
            is DataLoadingState.Success -> {
                _gameState.value = GameState().copy(gameList = state.data)
            }

            is DataLoadingState.Error -> {
                _gameState.value = GameState().copy(error = state.message)
                _uiEffect.emit(UiEffect.OnError(state.message.toString()))
            }

            is DataLoadingState.Loading -> {
                _gameState.value = GameState().copy(loading = true)
            }
        }

    }.launchIn(viewModelScope)

    fun onEvent(uiEvent: HomeUiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is HomeUiEvent.OnNavigation -> {
                    _uiEffect.emit(UiEffect.NavigateTo(uiEvent.id))
                }
            }
        }
    }
}