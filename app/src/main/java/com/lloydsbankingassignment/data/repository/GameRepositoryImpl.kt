package com.lloydsbankingassignment.data.repository

import com.lloydsbankingassignment.data.mapper.toDomainData
import com.lloydsbankingassignment.data.service.GameApi
import com.lloydsbankingassignment.data.service.ResponseCategory
import com.lloydsbankingassignment.domain.model.GameItem
import com.lloydsbankingassignment.domain.repository.GameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameApi: GameApi) : GameRepository {
    override fun getGameListing(): Flow<ResponseCategory<List<GameItem>>> = flow {
        emit(ResponseCategory.Loading())
        val result = gameApi.getGamesList().map { it.toDomainData() }
        emit(ResponseCategory.Success(result))
    }.flowOn(Dispatchers.IO).catch { emit(ResponseCategory.Error(it.message.toString())) }

    override fun getGameDetails(id: Int): Flow<ResponseCategory<GameItem>> = flow {
        emit(ResponseCategory.Loading())
        val result = gameApi.getGameDetails(id).toDomainData()
        emit(ResponseCategory.Success(result))
    }.flowOn(Dispatchers.IO).catch { emit(ResponseCategory.Error(it.message.toString())) }
}