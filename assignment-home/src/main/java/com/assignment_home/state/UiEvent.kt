package com.assignment_home.state

sealed interface UiEvent {
    class OnNavigation(val id: Int) : UiEvent
    object InitState : UiEvent
}
