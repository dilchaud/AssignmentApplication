package com.assignment_details.viewmodel

import androidx.lifecycle.ViewModel
import com.assignment_domain.usecases.DetailsApiUseCase
import com.data.service.DataLoadingState
import androidx.lifecycle.viewModelScope
import com.assignment_core.state.DetailsUiEvent
import com.assignment_core.state.GameState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: DetailsApiUseCase) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> get() = _gameState

    private fun getGameDetails(id: Int) = useCase(id).map { state ->
        when (state) {
            is DataLoadingState.Error -> {
                _gameState.value = GameState().copy(error = state.message)
            }

            is DataLoadingState.Loading -> {
                _gameState.value = GameState().copy(loading = true)
            }

            is DataLoadingState.Success -> {
                _gameState.value = GameState().copy(gameItem = state.data)
            }
        }
    }.launchIn(viewModelScope)

    fun onEvent(uiEvent: DetailsUiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is DetailsUiEvent.GetDetails -> {
                    getGameDetails(uiEvent.id)
                }
            }
        }
    }
}