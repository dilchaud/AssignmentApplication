package com.data

import com.assignment_domain.model.DataLoadingState
import com.assignment_domain.model.GameItem
import com.assignment_domain.repository.GameRepository
import com.data.remote.GameApi
import com.data.remote.dto.GameDetailsDto
import com.data.remote.dto.GameListDto
import com.data.remote.mapper.toDomainData
import com.data.repository.GameRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class GameRepositoryImplTest {
    private val dispatcher = TestCoroutineDispatcher()

    private var gameApi: GameApi = mockk()
    private var repository: GameRepository = mockk()
    private var repositoryImpl: GameRepositoryImpl = mockk()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repositoryImpl = GameRepositoryImpl(gameApi)
    }

    @Test
    fun getGameListingTest() = runBlocking {
        val apiResponse = buildHomeResponse()
        val expectedEventCount = 2
        coEvery { repository.getGameListing() } returns apiResponse

        val listOfEvents = repositoryImpl.getGameListing().toList()

        assertEquals(expectedEventCount, listOfEvents.size)
    }

    @Test
    fun getGameDetailsTest() = runBlocking {
        val id = 452
        val apiResponse = buildDetailsResponse()
        val expectedEventCount = 2
        coEvery { repository.getGameDetails(id) } returns apiResponse

        val listOfEvents = repositoryImpl.getGameDetails(id).toList()

        assertEquals(expectedEventCount, listOfEvents.size)
    }

    private fun buildHomeResponse() = flow<DataLoadingState<List<GameItem>>> {
        val result = buildHomeDetailsResponse().map { it.toDomainData() }
        emit(DataLoadingState.Success(result))
    }

    private fun buildDetailsResponse() = flow<DataLoadingState<GameItem>> {
        val result = buildGameDetailsResponse().toDomainData()
        emit(DataLoadingState.Success(result))
    }

    private fun buildGameDetailsResponse(): GameDetailsDto = GameDetailsDto(
        id = 452,
        title = "Call Of Duty: Warzone",
        thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/452\\/thumbnail.jpg",
        description = "Call of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes \\u2014 the general 150-player battle royle, and \\u201cPlunder\\u201d. The latter mode is described as a \\u201crace to deposit the most Cash\\u201d. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\\r\\n\\r\\nAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\\r\\n\\r\\nOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass."
    )

    private fun buildHomeDetailsResponse(): List<GameListDto> = listOf(
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

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun cleanUp() {
        dispatcher.cleanupTestCoroutines()
    }
}