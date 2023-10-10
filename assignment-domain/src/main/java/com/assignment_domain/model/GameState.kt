package com.assignment_domain.model



data class GameState(
    val gameList: List<GameItem>? = emptyList(),
    val gameItem: GameItem? = null,
    val error: String? = "",
    val loading: Boolean = false
)
