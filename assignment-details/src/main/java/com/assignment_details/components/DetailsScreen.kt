package com.assignment_details.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.assignment_details.mvicontract.UiEvent
import com.assignment_details.viewmodel.DetailsViewModel

@Composable
fun DetailsScreen(gameId: Int, viewModel: DetailsViewModel = hiltViewModel()) {
    viewModel.onEvent(UiEvent.GetDetails(gameId))
    val state = viewModel.gameState.collectAsStateWithLifecycle().value
    Surface {
        if (state.gameItem != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                state.gameItem?.run {
                    AsyncImage(
                        model = thumbnail, contentDescription = "", modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = title,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                        )
                        Text(text = description, modifier = Modifier.padding(10.dp))
                    }
                }
            }
        } else if (state.loading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}