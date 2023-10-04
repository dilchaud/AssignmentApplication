package com.lloydsbankingassignment.presentation.state

import com.lloydsbankingassignment.domain.model.GameItem

data class GameState(
    val gameList: List<GameItem>? = emptyList(),
    val gameItem: GameItem? = null,
    val error: String? = "",
    val loading: Boolean = false
)
