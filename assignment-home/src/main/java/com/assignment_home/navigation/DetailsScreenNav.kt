package com.assignment_home.navigation

sealed class DetailsScreenNav(val route: String) {
    object DetailsScreen : DetailsScreenNav("details_screen")
}
