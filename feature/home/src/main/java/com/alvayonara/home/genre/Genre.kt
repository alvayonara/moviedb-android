package com.alvayonara.home.genre

data class Genre(
    val genres: List<GenreData>
)

data class GenreData(
    val id: Int,
    val name: String
)