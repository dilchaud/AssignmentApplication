package com.lloydsbankingassignment.data.dto

import com.google.gson.annotations.SerializedName

data class GameListDto(
    val id: Int, val title: String, val thumbnail: String,
    @SerializedName("short_description")
    val shortDescription: String,
)
