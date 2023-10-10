package com.assignment_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment_details.state.DetailsUiEvent
import com.assignment_domain.model.GameState
import com.assignment_domain.usecases.DetailsApiUseCase
import com.assignment_domain.model.DataLoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val useCase: DetailsApiUseCase) : ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    val gameState: StateFlow<GameState> get() = _gameState.asStateFlow()

    private fun getGameDetails(id: Int) = useCase(id).map { state ->
        when (state) {
            is DataLoadingState.Error -> _gameState.update { GameState().copy(error = state.message) }

            is DataLoadingState.Loading -> _gameState.update { GameState().copy(loading = true) }

            is DataLoadingState.Success -> _gameState.update { GameState().copy(gameItem = state.data) }
        }
    }.launchIn(viewModelScope)

    fun onEvent(uiEvent: DetailsUiEvent) {
        viewModelScope.launch {
            when (uiEvent) {
                is DetailsUiEvent.GetDetails -> getGameDetails(uiEvent.id)
            }
        }
    }
}