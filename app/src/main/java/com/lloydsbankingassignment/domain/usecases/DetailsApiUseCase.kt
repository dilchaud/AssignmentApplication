package com.lloydsbankingassignment.domain.usecases

import com.lloydsbankingassignment.domain.repository.GameRepository
import javax.inject.Inject

class DetailsApiUseCase @Inject constructor(private val repository: GameRepository) {
    operator fun invoke(id: Int) = repository.getGameDetails(id)
}