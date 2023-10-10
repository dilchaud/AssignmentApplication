package com.assignment_details.state

sealed interface DetailsUiEvent {
    class GetDetails(val id: Int) : DetailsUiEvent
}
