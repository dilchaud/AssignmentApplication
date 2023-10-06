package com.lloydsdata.service

import com.lloydsdata.dto.GameDetailsDto
import com.lloydsdata.dto.GameListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface GameApi {
    @GET("games")
    suspend fun getGamesList(): List<GameListDto>

    @GET("game")
    suspend fun getGameDetails(@Query("id") id: Int): GameDetailsDto
}