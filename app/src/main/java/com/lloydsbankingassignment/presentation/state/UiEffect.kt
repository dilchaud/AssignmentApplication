package com.lloydsbankingassignment.presentation.state

sealed class UiEffect {
    class OnError(val errorMsg: String) : UiEffect()
    class NavigateTo(val id: Int) : UiEffect()
}
