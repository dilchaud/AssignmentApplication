package com.lloydsbankingassignment.data.service

import com.lloydsbankingassignment.data.dto.GameDetailsDto
import com.lloydsbankingassignment.data.dto.GameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET("games")
    suspend fun getGamesList(): List<GameListDto>

    @GET("game")
    suspend fun getGameDetails(@Query("id") id: Int): GameDetailsDto
}