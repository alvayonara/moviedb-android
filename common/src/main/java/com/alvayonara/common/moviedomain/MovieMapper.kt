package com.alvayonara.common.moviedomain

import com.alvayonara.common.extension.defaults

object MovieMapper {
    fun MovieResponse.mapToEntity(): Movie =
        Movie(
            page = this.page.defaults(0),
            results = this.results?.map { result ->
                Result(
                    adult = result.adult.defaults(false),
                    backdropPath = result.backdropPath.defaults(""),
                    genreIds = result.genreIds?.map { it }.defaults(listOf()),
                    id = result.id.defaults(0),
                    originalLanguage = result.originalLanguage.defaults("-"),
                    originalTitle = result.originalTitle.defaults("-"),
                    overview = result.overview.defaults("-"),
                    popularity = result.popularity.defaults(0.0),
                    posterPath = result.posterPath.defaults(""),
                    releaseDate = result.releaseDate.defaults(""),
                    title = result.title.defaults("-"),
                    video = result.video.defaults(false),
                    voteAverage = result.voteAverage.defaults(0.0),
                    voteCount = result.voteCount.defaults(0)
                )
            }.defaults(listOf()),
            totalPages = this.totalPages.defaults(0),
            totalResults = this.totalResults.defaults(0)
        )
}