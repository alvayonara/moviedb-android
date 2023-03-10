package com.alvayonara.home.ui

import android.content.res.Resources
import com.alvayonara.common.moviedomain.MovieType.DISCOVER
import com.alvayonara.common.moviedomain.MovieType.TRENDING
import com.alvayonara.common.moviedomain.Result
import com.alvayonara.home.genre.GenreData
import com.alvayonara.home.ui.DiscoverViews.DiscoverView
import com.alvayonara.home.ui.GenreViews.GenreView
import com.alvayonara.home.ui.HomeView.DiscoverSection
import com.alvayonara.home.ui.HomeView.GenreSection
import com.alvayonara.home.ui.HomeView.HeaderView
import com.alvayonara.home.ui.HomeView.TopView
import com.alvayonara.home.ui.HomeView.TrendingSection
import com.alvayonara.home.ui.TrendingViews.TrendingView
import com.alvayonara.moviedb_android.common.R.string

class HomeViewParser(
    private val resources: Resources
) {
    fun parse(
        discover: List<Result>,
        trending: List<Result>,
        genre: List<GenreData>
    ) {
        val result = mutableListOf<HomeView>()

        result.add(TopView)
        result.add(GenreSection(resources.getString(string.title_genre), generateGenre(genre)))
        result.add(
            DiscoverSection(
                id = resources.getString(string.title_discover),
                title = resources.getString(string.title_discover),
                subtitle = resources.getString(string.subtitle_discover),
                movieType = DISCOVER,
                discoverViews = generateDiscover(discover)
            )
        )
        result.add(
            TrendingSection(
                id = resources.getString(string.title_trending),
                title = resources.getString(string.title_trending),
                subtitle = resources.getString(string.subtitle_trending),
                movieType = TRENDING,
                trendingViews = generateTrending(trending),
            )
        )
    }

    private fun generateGenre(genreList: List<GenreData>): List<GenreViews> {
        val result = mutableListOf<GenreViews>()
        result.addAll(genreList.map {
            GenreView(it.name, it.id.toString())
        })

        return result
    }

    private fun generateDiscover(discoverList: List<Result>): List<DiscoverViews> {
        val result = mutableListOf<DiscoverViews>()
        result.addAll(discoverList.map {
            DiscoverView(it.title, it.posterPath, it.id.toString())
        })

        return result
    }

    private fun generateTrending(trendingList: List<Result>): List<TrendingViews> {
        val result = mutableListOf<TrendingViews>()
        result.addAll(trendingList.map {
            TrendingView(it.title, it.posterPath, it.id.toString())
        })

        return result
    }
}