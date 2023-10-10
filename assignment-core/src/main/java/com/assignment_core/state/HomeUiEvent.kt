package com.assignment_core.state

sealed interface HomeUiEvent {
    class OnNavigation(val id: Int) : HomeUiEvent
}
