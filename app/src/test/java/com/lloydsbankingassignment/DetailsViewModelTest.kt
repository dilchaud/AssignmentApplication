package com.lloydsbankingassignment

import com.lloydsbankingassignment.data.dto.GameDetailsDto
import com.lloydsbankingassignment.data.mapper.toDomainData
import com.lloydsbankingassignment.data.repository.GameRepositoryImpl
import com.lloydsbankingassignment.data.service.GameApi
import com.lloydsbankingassignment.data.service.ResponseCategory
import com.lloydsbankingassignment.domain.model.GameItem
import com.lloydsbankingassignment.domain.repository.GameRepository
import com.lloydsbankingassignment.domain.usecases.DetailsApiUseCase
import com.lloydsbankingassignment.presentation.details.DetailsViewModel
import com.lloydsbankingassignment.presentation.state.DetailsUiEvent
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

class DetailsViewModelTest {
    private val dispatcher = TestCoroutineDispatcher()

    @MockK
    lateinit var gameApi: GameApi

    @MockK
    lateinit var repositoryImpl: GameRepositoryImpl

    @MockK
    lateinit var repository: GameRepository

    @MockK
    lateinit var viewModel: DetailsViewModel

    @MockK
    lateinit var useCase: DetailsApiUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)
        gameApi = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = DetailsApiUseCase(repository)
        repositoryImpl = GameRepositoryImpl(gameApi)
        viewModel = DetailsViewModel(useCase)
    }

    @Test
    fun getGameDetailsSuccessTest() {
        val id = 452
        val event = DetailsUiEvent.GetDetails(id)
        val apiResponse = buildSuccessResponse()
        val expectedResponse = buildGameDetailsResponse().toDomainData()

        coEvery { useCase(id) } returns apiResponse
        viewModel.onEvent(event)

        assertEquals(expectedResponse, viewModel.gameState.value.gameItem)
    }

    @Test
    fun getGameDetailsLoadingTest() {
        val id = 452
        val event = DetailsUiEvent.GetDetails(id)
        val apiResponse = buildLoadingResponse()

        coEvery { useCase(id) } returns apiResponse
        viewModel.onEvent(event)

        assertEquals(true, viewModel.gameState.value.loading)
    }

    @Test
    fun getGameDetailsErrorTest() {
        val id = 452
        val event = DetailsUiEvent.GetDetails(id)
        val apiResponse = buildErrorResponse()
        val expectedError = buildErrorMsg()

        coEvery { useCase(id) } returns apiResponse
        viewModel.onEvent(event)

        assertEquals(expectedError, viewModel.gameState.value.error)
    }

    private fun buildSuccessResponse() = flow<ResponseCategory<GameItem>> {
        val result = buildGameDetailsResponse().toDomainData()
        emit(ResponseCategory.Success(result))
    }

    private fun buildErrorResponse() = flow<ResponseCategory<GameItem>> {
        emit(ResponseCategory.Error(buildErrorMsg()))
    }

    private fun buildLoadingResponse() = flow<ResponseCategory<GameItem>> {
        emit(ResponseCategory.Loading())
    }

    private fun buildErrorMsg(): String = "NetworkError"

    private fun buildGameDetailsResponse(): GameDetailsDto {
        return GameDetailsDto(
            id = 452,
            title = "Call Of Duty: Warzone",
            thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/452\\/thumbnail.jpg",
            description = "Call of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes \\u2014 the general 150-player battle royle, and \\u201cPlunder\\u201d. The latter mode is described as a \\u201crace to deposit the most Cash\\u201d. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\\r\\n\\r\\nAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\\r\\n\\r\\nOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass."
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        dispatcher.cleanupTestCoroutines()
    }
}