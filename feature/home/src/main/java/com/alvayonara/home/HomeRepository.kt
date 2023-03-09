package com.alvayonara.home

import com.alvayonara.common.moviedomain.Movie
import com.alvayonara.common.moviedomain.MovieMapper.mapToEntity
import com.alvayonara.home.genre.GenreData
import com.alvayonara.home.genre.GenreMapper.mapToEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface HomeRepository {
    fun getDiscoverMovie(): Observable<Movie>
    fun getTrendingMovie(): Observable<Movie>
    fun getGenre(): Observable<List<GenreData>>
}

class HomeRepositoryImpl @Inject constructor(private val homeService: HomeService): HomeRepository {
    override fun getDiscoverMovie(): Observable<Movie> = homeService.getDiscoverMovie().map { it.mapToEntity() }

    override fun getTrendingMovie(): Observable<Movie> = homeService.getTrendingMovie().map { it.mapToEntity() }

    override fun getGenre(): Observable<List<GenreData>> = homeService.getGenres().map { it.mapToEntity() }
}