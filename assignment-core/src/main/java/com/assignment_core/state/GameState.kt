package com.assignment_core.state

import com.data.model.GameItem


data class GameState(
    val gameList: List<GameItem>? = emptyList(),
    val gameItem: GameItem? = null,
    val error: String? = "",
    val loading: Boolean = false
)
