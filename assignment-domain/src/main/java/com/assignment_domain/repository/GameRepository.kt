package com.assignment_domain.repository

import com.data.service.DataLoadingState
import com.data.model.GameItem
import kotlinx.coroutines.flow.Flow


interface GameRepository {
    fun getGameListing(): Flow<DataLoadingState<List<GameItem>>>

    fun getGameDetails(id:Int): Flow<DataLoadingState<GameItem>>
}