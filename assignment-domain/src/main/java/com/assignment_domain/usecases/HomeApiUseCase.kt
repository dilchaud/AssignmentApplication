package com.assignment_domain.usecases

import com.assignment_domain.repository.GameRepository
import javax.inject.Inject

class HomeApiUseCase @Inject constructor(private val repository: GameRepository) {
    operator fun invoke() = repository.getGameListing()
}