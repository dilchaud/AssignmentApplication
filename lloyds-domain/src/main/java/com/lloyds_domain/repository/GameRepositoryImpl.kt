package com.lloyds_domain.repository

import com.lloydsdata.mapper.toDomainData
import com.lloydsdata.model.GameItem
import com.lloydsdata.service.GameApi
import com.lloydsdata.service.DataLoadingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameApi: GameApi) : GameRepository {
    override fun getGameListing(): Flow<DataLoadingState<List<GameItem>>> = flow {
        emit(DataLoadingState.Loading())
        val result = gameApi.getGamesList().map { it.toDomainData() }
        emit(DataLoadingState.Success(result))
    }.flowOn(Dispatchers.IO).catch { emit(DataLoadingState.Error(it.message.toString())) }

    override fun getGameDetails(id: Int): Flow<DataLoadingState<GameItem>> = flow {
        emit(DataLoadingState.Loading())
        val result = gameApi.getGameDetails(id).toDomainData()
        emit(DataLoadingState.Success(result))
    }.flowOn(Dispatchers.IO).catch { emit(DataLoadingState.Error(it.message.toString())) }
}