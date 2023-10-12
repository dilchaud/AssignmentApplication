package com.assignment.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.assignment.presentation.MainActivity.Companion.GAME_ID
import com.assignment_details.components.DetailsScreen
import com.assignment_home.components.HomeScreen
import com.assignment_home.navigation.DetailsScreenNav

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController, startDestination: String) {

    Scaffold {
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = Modifier.padding(it)
        ) {

            composable(Screen.HomeScreen.route) {
                HomeScreen(navController)
            }

            composable(
                DetailsScreenNav.DetailsScreen.route + "/{$GAME_ID}",
                arguments = listOf(navArgument(
                    GAME_ID
                ) { type = NavType.IntType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                DetailsScreen(gameId = arguments.getInt(GAME_ID))
            }
        }
    }
}