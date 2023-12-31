package com.assignment_domain.repository


import com.assignment_domain.model.DataLoadingState
import com.assignment_domain.model.GameItem
import kotlinx.coroutines.flow.Flow


interface GameRepository {
    fun getGameListing(): Flow<DataLoadingState<List<GameItem>>>

    fun getGameDetails(id: Int): Flow<DataLoadingState<GameItem>>
}