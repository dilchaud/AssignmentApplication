package com.assignment_home.mvicontract

sealed interface UiEffect {
    class OnError(val errorMsg: String) : UiEffect
    class NavigateTo(val id: Int) : UiEffect
}
