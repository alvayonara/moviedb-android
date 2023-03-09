package com.alvayonara.detail.moviedetail

import com.alvayonara.common.extension.defaults

object MovieDetailMapper {
    fun MovieDetailResponse.mapToEntity(): MovieDetail =
        MovieDetail(
            backdropPath = this.backdropPath.defaults(""),
            genres = this.genres?.map { genre ->
                Genre(
                    id = genre.id.defaults(0),
                    name = genre.name.defaults("")
                )
            }.defaults(listOf()),
            id = this.id.defaults(0),
            originalLanguage = this.originalLanguage.defaults(""),
            originalTitle = this.originalTitle.defaults(""),
            overview = this.overview.defaults(""),
            popularity = this.popularity.defaults(0.0),
            posterPath = this.posterPath.defaults(""),
            releaseDate = this.releaseDate.defaults(""),
            status = this.status.defaults(""),
            title = this.title.defaults(""),
            video = this.video.defaults(false),
            voteAverage = this.voteAverage.defaults(0.0),
            voteCount = this.voteCount.defaults(0)
        )
}