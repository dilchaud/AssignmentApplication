package com.lloydsdata.mapper

import com.lloydsdata.dto.GameDetailsDto
import com.lloydsdata.dto.GameListDto
import com.lloydsdata.model.GameItem

fun GameListDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", shortDescription ?: "")

fun GameDetailsDto.toDomainData(): GameItem =
    GameItem(id, title ?: "", thumbnail ?: "", description ?: "")