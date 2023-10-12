package com.assignment_home.mvicontract

sealed interface UiEvent {
    class OnNavigation(val id: Int) : UiEvent
    object InitState : UiEvent
}
