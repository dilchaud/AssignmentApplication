package com.assignment_core.state

sealed interface DetailsUiEvent {
    class GetDetails(val id: Int) : DetailsUiEvent
}
