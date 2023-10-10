package com.data.mapper

import com.data.dto.GameDetailsDto
import com.data.dto.GameListDto
import com.data.model.GameItem

fun GameListDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", shortDescription ?: "")

fun GameDetailsDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", description ?: "")