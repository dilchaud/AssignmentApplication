package com.lloydsbankingassignment.data.mapper

import com.lloydsbankingassignment.data.dto.GameDetailsDto
import com.lloydsbankingassignment.data.dto.GameListDto
import com.lloydsbankingassignment.domain.model.GameItem

fun GameListDto.toDomainData(): GameItem {
    return GameItem(id, title, thumbnail, shortDescription)
}

fun GameDetailsDto.toDomainData(): GameItem {
    return GameItem(id, title, thumbnail, description)
}