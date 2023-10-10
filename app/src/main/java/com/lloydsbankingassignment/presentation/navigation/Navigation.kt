package com.lloydsbankingassignment.presentation.navigation

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.assignment_core.state.DetailsUiEvent
import com.assignment_core.state.UiEffect
import com.assignment_core.util.Constants.GAME_ID
import com.assignment_details.components.DetailsScreen
import com.assignment_details.viewmodel.DetailsViewModel
import com.assignment_home.components.HomeScreen
import com.assignment_home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(navController: NavHostController) {

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) {
        NavHost(
            navController = navController,
            startDestination = Screen.HomeScreen.route,
            modifier = Modifier.padding(it)
        ) {

            composable(Screen.HomeScreen.route) {
                val viewModel = hiltViewModel<HomeViewModel>()
                val state = viewModel.gameState.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = true) {
                    viewModel.uiEffect.collectLatest { effect ->
                        when (effect) {
                            is UiEffect.OnError -> {
                                launch {
                                    snackBarHostState.showSnackbar(
                                        effect.errorMsg,
                                        duration = SnackbarDuration.Long
                                    )
                                }
                            }

                            is UiEffect.NavigateTo -> {
                                Log.d("Dilip", "Inside navigation")
                                navController.navigate(Screen.DetailsScreen.route + "/${effect.id}")
                            }
                        }
                    }
                }
                HomeScreen(state = state.value, modifier = Modifier, viewModel = viewModel)
            }

            composable(
                Screen.DetailsScreen.route + "/{$GAME_ID}", arguments = listOf(navArgument(
                    GAME_ID
                ) { type = NavType.IntType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val viewModel = hiltViewModel<DetailsViewModel>()
                viewModel.onEvent(DetailsUiEvent.GetDetails(arguments.getInt(GAME_ID)))
                val state = viewModel.gameState.collectAsStateWithLifecycle()
                Log.d("Dilip", "Inside navigation    composable")
                DetailsScreen(state = state.value)
            }
        }
    }
}