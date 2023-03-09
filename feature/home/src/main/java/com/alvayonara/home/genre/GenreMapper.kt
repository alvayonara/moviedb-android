package com.alvayonara.home.genre

import com.alvayonara.common.extension.defaults

object GenreMapper {
    fun GenreResponse.mapToEntity(): List<GenreData> =
        this.genres?.map { genre ->
            GenreData(
                id = genre.id.defaults(0),
                name = genre.name.defaults("-")
            )
        }.defaults(listOf())
}