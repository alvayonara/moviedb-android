package com.alvayonara.movies.list

import com.alvayonara.common.moviedomain.Movie
import com.alvayonara.common.moviedomain.MovieMapper.mapToEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface MoviesRepository {
    fun getDiscoverMovie(page: Int): Observable<Movie>
    fun getTrendingMovie(page: Int): Observable<Movie>
}

class MoviesRepositoryImpl @Inject constructor(
    private val moviesService: MoviesService
) : MoviesRepository {
    override fun getDiscoverMovie(page: Int): Observable<Movie> =
        moviesService.getDiscoverMovie(page).map { it.mapToEntity() }

    override fun getTrendingMovie(page: Int): Observable<Movie> =
        moviesService.getTrendingMovie(page).map { it.mapToEntity() }
}