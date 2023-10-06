package com.lloyds_home


import com.lloyds_domain.repository.GameRepository
import com.lloyds_domain.repository.GameRepositoryImpl
import com.lloyds_domain.usecases.HomeApiUseCase
import com.lloyds_home.viewmodel.HomeViewModel
import com.lloydsdata.dto.GameListDto
import com.lloydsdata.mapper.toDomainData
import com.lloydsdata.model.GameItem
import com.lloydsdata.service.GameApi
import com.lloydsdata.service.DataLoadingState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    private val dispatcher = TestCoroutineDispatcher()

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

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        gameApi = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = HomeApiUseCase(repository)
        repositoryImpl = GameRepositoryImpl(gameApi)
    }

    @Test
    fun getAllGameListSuccessTest() {
        val apiResponse = buildSuccessResponse()
        val expectedResponse = buildGameDetailsResponse().map { it.toDomainData() }

        coEvery { useCase() } returns apiResponse
        viewModel = HomeViewModel(useCase)

        assertEquals(expectedResponse.size, viewModel.gameState.value.gameList?.size)
    }

    @Test
    fun getAllGameListErrorTest() {
        val apiResponse = buildErrorResponse()
        val expectedError = buildErrorMsg()

        coEvery { useCase() } returns apiResponse
        viewModel = HomeViewModel(useCase)

        assertEquals(expectedError, viewModel.gameState.value.error)
    }

    @Test
    fun getAllGameListLoadingTest() {
        val apiResponse = buildLoadingResponse()

        coEvery { useCase() } returns apiResponse
        viewModel = HomeViewModel(useCase)

        assertEquals(true, viewModel.gameState.value.loading)
    }

    private fun buildSuccessResponse() = flow<DataLoadingState<List<GameItem>>> {
        val result = buildGameDetailsResponse().map { it.toDomainData() }
        emit(DataLoadingState.Success(result))
    }

    private fun buildErrorResponse() = flow<DataLoadingState<List<GameItem>>> {
        emit(DataLoadingState.Error(buildErrorMsg()))
    }

    private fun buildLoadingResponse() = flow<DataLoadingState<List<GameItem>>> {
        emit(DataLoadingState.Loading())
    }

    private fun buildErrorMsg(): String = "NetworkError while fetching list data"


    private fun buildGameDetailsResponse(): List<GameListDto> {
        return listOf(
            GameListDto(
                id = 540,
                title = "Overwatch",
                thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/540\\/thumbnail.jpg",
                shortDescription = "A hero-focused first-person team shooter from Blizzard Entertainment."
            ), GameListDto(
                id = 521,
                title = "Diablo Immortal",
                thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/521\\/thumbnail.jpg",
                shortDescription = "Built for mobile and also released on PC, Diablo Immortal fills in the gaps between Diablo II and III in an MMOARPG environment."
            )
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        dispatcher.cleanupTestCoroutines()
    }

}