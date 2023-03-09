package com.alvayonara.home

import com.alvayonara.common.moviedomain.Result
import com.alvayonara.home.genre.GenreData

data class HomeData(
    val discover: List<Result>,
    val trending: List<Result>,
    val genre: List<GenreData>
)
