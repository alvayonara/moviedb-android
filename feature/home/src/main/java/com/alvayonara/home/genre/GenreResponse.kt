package com.alvayonara.home.genre


import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<GenreDataResponse>? = null
)

data class GenreDataResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null
)