package com.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GameListDto(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("title") val title: String? = null,
    @SerializedName("thumbnail") val thumbnail: String? = null,
    @SerializedName("short_description") val shortDescription: String? = null,
)
