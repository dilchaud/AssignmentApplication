package com.lloyds_home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.lloyds_core.state.GameState
import com.lloyds_core.state.HomeUiEvent
import com.lloyds_home.viewmodel.HomeViewModel

@Composable
fun HomeScreen(state: GameState, modifier: Modifier, viewModel: HomeViewModel) {
    if (state.gameList?.isNotEmpty() == true) {
        LazyColumn {
            items(state.gameList!!) { item ->
                HomeScreenItem(modifier, item, onItemClick = {
                    viewModel.onEvent(HomeUiEvent.OnNavigation(it))
                })
            }
        }
    } else if (state.loading) {
        Box(modifier = modifier.fillMaxSize()) {
            CircularProgressIndicator(modifier = modifier.align(Alignment.Center))
        }
    }
}