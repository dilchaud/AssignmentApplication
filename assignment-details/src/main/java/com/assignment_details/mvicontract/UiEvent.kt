package com.assignment_details.mvicontract

sealed interface UiEvent {
    class GetDetails(val id: Int) : UiEvent
}
