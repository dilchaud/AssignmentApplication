package com.lloydsbankingassignment.presentation.state

sealed class DetailsUiEvent {
    class GetDetails(val id: Int) : DetailsUiEvent()
}
