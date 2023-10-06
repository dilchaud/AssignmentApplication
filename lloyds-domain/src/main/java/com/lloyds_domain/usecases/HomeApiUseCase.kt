package com.lloyds_domain.usecases

import com.lloyds_domain.repository.GameRepository
import javax.inject.Inject

class HomeApiUseCase @Inject constructor(private val repository: GameRepository) {
    operator fun invoke() = repository.getGameListing()
}