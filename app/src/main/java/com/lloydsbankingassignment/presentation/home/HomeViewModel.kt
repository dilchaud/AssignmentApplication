package com.lloydsbankingassignment.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydsbankingassignment.data.service.ResponseCategory
import com.lloydsbankingassignment.domain.usecases.HomeApiUseCase
import com.lloydsbankingassignment.presentation.state.GameState
import com.lloydsbankingassignment.presentation.state.UiEffect
import com.lloydsbankingassignment.presentation.state.HomeUiEvent
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
            is ResponseCategory.Success -> {
                _gameState.value = GameState().copy(gameList = state.data)
            }

            is ResponseCategory.Error -> {
                _gameState.value = GameState().copy(error = state.message)
                _uiEffect.emit(UiEffect.OnError(state.message.toString()))
            }

            is ResponseCategory.Loading -> {
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