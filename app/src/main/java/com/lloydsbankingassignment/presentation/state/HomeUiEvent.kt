package com.lloydsbankingassignment.presentation.state

sealed class HomeUiEvent {
    class OnNavigation(val id: Int) : HomeUiEvent()
}
