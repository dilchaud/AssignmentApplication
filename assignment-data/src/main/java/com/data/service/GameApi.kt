package com.data.service

import com.data.dto.GameDetailsDto
import com.data.dto.GameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET("games")
    suspend fun getGamesList(): List<GameListDto>

    @GET("game")
    suspend fun getGameDetails(@Query("id") id: Int): GameDetailsDto
}