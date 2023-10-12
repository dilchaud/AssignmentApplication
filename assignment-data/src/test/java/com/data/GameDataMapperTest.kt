package com.data

import com.assignment_domain.model.GameItem
import com.data.remote.dto.GameDetailsDto
import com.data.remote.dto.GameListDto
import com.data.remote.mapper.toDomainData
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GameDataMapperTest {

    @Test
    fun gameListToDomainSuccessTest() {
        val response = buildListSuccessResponse().map { it.toDomainData() }
        val expected = buildListExpectedResponse()
        assertEquals(expected, response)
    }

    @Test
    fun gameListToDomainNullTest() {
        val response = buildListNullResponse().map { it.toDomainData() }
        val expected = buildListExpectedNullResponse()
        assertEquals(expected, response)
    }

    @Test
    fun gameDetailsToDomainSuccessTest() {
        val response = buildGameDetailsSuccessResponse().toDomainData()
        val expected = buildDetailsExpectedResponse()
        assertEquals(expected, response)
    }

    @Test
    fun gameDetailsToDomainNullTest() {
        val response = buildGameDetailsNullResponse().toDomainData()
        val expected = buildDetailsExpectedNullResponse()
        assertEquals(expected, response)
    }

    private fun buildGameDetailsNullResponse(): GameDetailsDto = GameDetailsDto(id = 452)

    private fun buildDetailsExpectedNullResponse(): GameItem =
        GameItem(id = 452, title = "", thumbnail = "", description = "")

    private fun buildGameDetailsSuccessResponse(): GameDetailsDto = GameDetailsDto(
        id = 452,
        title = "Call Of Duty: Warzone",
        thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/452\\/thumbnail.jpg",
        description = "Call of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes \\u2014 the general 150-player battle royle, and \\u201cPlunder\\u201d. The latter mode is described as a \\u201crace to deposit the most Cash\\u201d. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\\r\\n\\r\\nAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\\r\\n\\r\\nOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass."
    )

    private fun buildDetailsExpectedResponse(): GameItem = GameItem(
        id = 452,
        title = "Call Of Duty: Warzone",
        thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/452\\/thumbnail.jpg",
        description = "Call of Duty: Warzone is both a standalone free-to-play battle royale and modes accessible via Call of Duty: Modern Warfare. Warzone features two modes \\u2014 the general 150-player battle royle, and \\u201cPlunder\\u201d. The latter mode is described as a \\u201crace to deposit the most Cash\\u201d. In both modes players can both earn and loot cash to be used when purchasing in-match equipment, field upgrades, and more. Both cash and XP are earned in a variety of ways, including completing contracts.\\r\\n\\r\\nAn interesting feature of the game is one that allows players who have been killed in a match to rejoin it by winning a 1v1 match against other felled players in the Gulag.\\r\\n\\r\\nOf course, being a battle royale, the game does offer a battle pass. The pass offers players new weapons, playable characters, Call of Duty points, blueprints, and more. Players can also earn plenty of new items by completing objectives offered with the pass."
    )

    private fun buildListExpectedResponse(): List<GameItem> = listOf(
        GameItem(
            id = 540,
            title = "Overwatch",
            thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/540\\/thumbnail.jpg",
            description = "A hero-focused first-person team shooter from Blizzard Entertainment."
        ), GameItem(
            id = 521,
            title = "Diablo Immortal",
            thumbnail = "https:\\/\\/www.freetogame.com\\/g\\/521\\/thumbnail.jpg",
            description = "Built for mobile and also released on PC, Diablo Immortal fills in the gaps between Diablo II and III in an MMOARPG environment."
        )
    )

    private fun buildListSuccessResponse(): List<GameListDto> = listOf(
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

    private fun buildListNullResponse(): List<GameListDto> = listOf(
        GameListDto(
            id = 540, title = "Overwatch"
        ), GameListDto(
            id = 521
        )
    )

    private fun buildListExpectedNullResponse(): List<GameItem> = listOf(
        GameItem(
            id = 540, title = "Overwatch", thumbnail = "", description = ""
        ), GameItem(
            id = 521, title = "", thumbnail = "", description = ""
        )
    )

}