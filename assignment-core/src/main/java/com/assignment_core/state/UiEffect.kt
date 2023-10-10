package com.assignment_core.state

sealed interface UiEffect {
    class OnError(val errorMsg: String) : UiEffect
    class NavigateTo(val id: Int) : UiEffect
}
