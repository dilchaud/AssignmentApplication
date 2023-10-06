package com.lloyds_core.state

import com.lloydsdata.model.GameItem


data class GameState(
    val gameList: List<GameItem>? = emptyList(),
    val gameItem: GameItem? = null,
    val error: String? = "",
    val loading: Boolean = false
)
