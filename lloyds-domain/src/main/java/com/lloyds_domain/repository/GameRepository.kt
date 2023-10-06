package com.lloyds_domain.repository

import com.lloydsdata.service.DataLoadingState
import com.lloydsdata.model.GameItem
import kotlinx.coroutines.flow.Flow


interface GameRepository {
    fun getGameListing(): Flow<DataLoadingState<List<GameItem>>>

    fun getGameDetails(id:Int): Flow<DataLoadingState<GameItem>>
}