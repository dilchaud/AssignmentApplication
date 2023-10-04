package com.lloydsbankingassignment


import com.lloydsbankingassignment.data.repository.GameRepositoryImpl
import com.lloydsbankingassignment.data.service.GameApi
import com.lloydsbankingassignment.domain.repository.GameRepository
import com.lloydsbankingassignment.domain.usecases.HomeApiUseCase
import com.lloydsbankingassignment.presentation.home.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain

import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    @MockK
    lateinit var gameApi: GameApi

    @MockK
    lateinit var repositoryImpl: GameRepositoryImpl

    @MockK
    lateinit var repository: GameRepository

    @MockK
    lateinit var viewModel: HomeViewModel

    @MockK
    lateinit var useCase: HomeApiUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        gameApi = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = HomeApiUseCase(repository)
        repositoryImpl = GameRepositoryImpl(gameApi)
        viewModel = HomeViewModel(useCase)
    }

    @Test
    fun getAllGameListSuccessTest() {

    }

}