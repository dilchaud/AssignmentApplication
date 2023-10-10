package com.assignment_details.state

sealed interface UiEvent {
    class GetDetails(val id: Int) : UiEvent
}
