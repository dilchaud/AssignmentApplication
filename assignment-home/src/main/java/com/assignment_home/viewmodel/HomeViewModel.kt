package com.assignment_home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment_domain.model.GameState
import com.assignment_home.mvicontract.UiEvent
import com.assignment_home.mvicontract.UiEffect
import com.assignment_domain.usecases.HomeApiUseCase
import com.assignment_domain.model.DataLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: HomeApiUseCase) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> get() = _gameState.asStateFlow()

    private val _uiEffect = MutableSharedFlow<UiEffect>()
    val uiEffect = _uiEffect.asSharedFlow()

    private fun getAllGameList() = useCase().onEach { state ->
        when (state) {
            is DataLoadingState.Success -> {
                _gameState.update { GameState().copy(gameList = state.data) }
            }

            is DataLoadingState.Error -> {
                _gameState.update { GameState().copy(error = state.message) }
                _uiEffect.emit(UiEffect.OnError(state.message.toString()))
            }

            is DataLoadingState.Loading -> _gameState.update { GameState().copy(loading = true) }
        }

    }.launchIn(viewModelScope)

    fun onEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is UiEvent.OnNavigation -> _uiEffect.emit(UiEffect.NavigateTo(uiEvent.id))

                is UiEvent.InitState -> getAllGameList()
            }
        }
    }
}