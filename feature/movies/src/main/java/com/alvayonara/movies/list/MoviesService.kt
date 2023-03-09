package com.alvayonara.movies.list

import com.alvayonara.common.moviedomain.MovieResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesService {
    @GET("discover/movie")
    fun getDiscoverMovie(
        @Query("page") page: Int
    ): Observable<MovieResponse>

    @GET("trending/all/day")
    fun getTrendingMovie(
        @Query("page") page: Int
    ): Observable<MovieResponse>
}