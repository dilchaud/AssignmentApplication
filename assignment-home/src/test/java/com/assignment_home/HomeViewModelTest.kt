package com.assignment_home


import com.assignment_domain.model.DataLoadingState
import com.assignment_domain.model.GameItem
import com.assignment_domain.repository.GameRepository
import com.assignment_domain.usecases.HomeApiUseCase
import com.assignment_home.state.UiEvent
import com.assignment_home.viewmodel.HomeViewModel
import com.data.remote.GameApi
import com.data.remote.dto.GameListDto
import com.data.remote.mapper.toDomainData
import com.data.repository.GameRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
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

    private lateinit var gameApi: GameApi
    private lateinit var repositoryImpl: GameRepositoryImpl
    private lateinit var repository: GameRepository
    private lateinit var viewModel: HomeViewModel
    private lateinit var useCase: HomeApiUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
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
        val apiResponse = buildSuccessResponse()
        val expectedResponse = buildGameDetailsResponse().map { it.toDomainData() }
        val event = UiEvent.InitState

        coEvery { useCase() } returns apiResponse
        viewModel.onEvent(event)

        assertEquals(expectedResponse.size, viewModel.gameState.value.gameList?.size)
    }

    @Test
    fun getAllGameListErrorTest() {
        val apiResponse = buildErrorResponse()
        val expectedError = buildErrorMsg()
        val event = UiEvent.InitState

        coEvery { useCase() } returns apiResponse
        viewModel.onEvent(event)

        assertEquals(expectedError, viewModel.gameState.value.error)
    }

    @Test
    fun getAllGameListLoadingTest() {
        val apiResponse = buildLoadingResponse()
        val event = UiEvent.InitState

        coEvery { useCase() } returns apiResponse
        viewModel.onEvent(event)

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