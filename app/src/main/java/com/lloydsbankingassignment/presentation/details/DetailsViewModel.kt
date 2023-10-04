package com.lloydsbankingassignment.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lloydsbankingassignment.data.service.ResponseCategory
import com.lloydsbankingassignment.domain.usecases.DetailsApiUseCase
import com.lloydsbankingassignment.presentation.state.DetailsUiEvent
import com.lloydsbankingassignment.presentation.state.GameState
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
            is ResponseCategory.Error -> {
                _gameState.value = GameState().copy(error = state.message)
            }

            is ResponseCategory.Loading -> {
                _gameState.value = GameState().copy(loading = true)
            }

            is ResponseCategory.Success -> {
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