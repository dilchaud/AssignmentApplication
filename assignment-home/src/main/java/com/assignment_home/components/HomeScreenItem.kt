package com.assignment_home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.assignment_domain.model.GameItem

@Composable
fun HomeScreenItem(modifier: Modifier, item: GameItem, onItemClick: (Int) -> Unit) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Card(elevation = CardDefaults.cardElevation(4.dp), shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clickable { item.id?.let { onItemClick(it) } }

            ) {
                AsyncImage(
                    model = item.thumbnail, contentDescription = "", modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = item.title,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                Text(text = item.description, maxLines = 2, modifier = Modifier.padding(10.dp))
            }
        }
    }
}