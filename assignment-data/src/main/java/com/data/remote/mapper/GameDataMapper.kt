package com.data.remote.mapper

import com.assignment_domain.model.GameItem
import com.data.remote.dto.GameDetailsDto
import com.data.remote.dto.GameListDto

fun GameListDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", shortDescription ?: "")

fun GameDetailsDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", description ?: "")