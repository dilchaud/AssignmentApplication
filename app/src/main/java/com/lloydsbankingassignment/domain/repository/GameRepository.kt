package com.lloydsbankingassignment.domain.repository

import com.lloydsbankingassignment.data.service.ResponseCategory
import com.lloydsbankingassignment.domain.model.GameItem
import kotlinx.coroutines.flow.Flow


interface GameRepository {
    fun getGameListing(): Flow<ResponseCategory<List<GameItem>>>

    fun getGameDetails(id:Int): Flow<ResponseCategory<GameItem>>
}