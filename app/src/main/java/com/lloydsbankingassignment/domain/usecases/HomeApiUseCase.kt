package com.lloydsbankingassignment.domain.usecases

import com.lloydsbankingassignment.domain.repository.GameRepository
import javax.inject.Inject

class HomeApiUseCase @Inject constructor(private val repository: GameRepository) {
    operator fun invoke() = repository.getGameListing()
}