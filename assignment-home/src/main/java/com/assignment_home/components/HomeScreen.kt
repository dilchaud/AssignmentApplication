package com.assignment_home.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.assignment_home.mvicontract.UiEffect
import com.assignment_home.mvicontract.UiEvent
import com.assignment_home.navigation.DetailsScreenNav
import com.assignment_home.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.gameState.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.onEvent(UiEvent.InitState)
        viewModel.uiEffect.collectLatest { effect ->
            when (effect) {
                is UiEffect.OnError -> Toast.makeText(context, effect.errorMsg, Toast.LENGTH_LONG)
                    .show()

                is UiEffect.NavigateTo -> navController.navigate(DetailsScreenNav.DetailsScreen.route + "/${effect.id}")
            }
        }
    }

    val list = state.gameList
    if (list?.isNotEmpty() == true) {
        LazyColumn {
            items(list) { item ->
                HomeScreenItem(modifier, item, onItemClick = {
                    viewModel.onEvent(UiEvent.OnNavigation(it))
                })
            }
        }
    } else if (state.loading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        }
    }
}