package com.lloydsdata.dto

import com.google.gson.annotations.SerializedName

data class GameDetailsDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("description") val description: String? = null
)
