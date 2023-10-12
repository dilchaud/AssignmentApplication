package com.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GameDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("description") val description: String? = null
)
