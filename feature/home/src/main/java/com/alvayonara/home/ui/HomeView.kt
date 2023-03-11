package com.alvayonara.home.ui

import com.alvayonara.common.moviedomain.MovieType

sealed class HomeView(
    open val id: String
) {
    object TopView : HomeView("top")

    data class HeaderView(
        override val id: String,
        val title: String,
        val subtitle: String,
        val movieType: MovieType,
    ) : HomeView(id)

    data class GenreSection(
        override val id: String,
        val genreViews: List<GenreViews>
    ) : HomeView(id)

    data class DiscoverSection(
        override val id: String,
        val title: String,
        val subtitle: String,
        val movieType: MovieType,
        val discoverViews: List<DiscoverViews>
    ) : HomeView(id)

    data class TrendingSection(
        override val id: String,
        val title: String,
        val subtitle: String,
        val movieType: MovieType,
        val trendingViews: List<TrendingViews>
    ) : HomeView(id)
}

sealed class GenreViews(
    open val id: String
) {
    data class GenreView(
        override val id: String,
        val genre: String
    ) : GenreViews(id)
}

sealed class DiscoverViews(
    open val id: String
) {
    data class DiscoverView(
        override val id: String,
        val movieId: Int,
        val title: String,
        val poster: String
    ) : DiscoverViews(id)
}

sealed class TrendingViews(
    open val id: String
) {
    data class TrendingView(
        override val id: String,
        val movieId: Int,
        val title: String,
        val poster: String
    ) : TrendingViews(id)
}