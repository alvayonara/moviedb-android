package com.alvayonara.home

import com.alvayonara.common.moviedomain.MovieResponse
import com.alvayonara.home.genre.GenreResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface HomeService {
    @GET("discover/movie")
    fun getDiscoverMovie(): Observable<MovieResponse>

    @GET("genre/movie/list")
    fun getGenres(): Observable<GenreResponse>

    @GET("trending/all/day")
    fun getTrendingMovie(): Observable<MovieResponse>
}