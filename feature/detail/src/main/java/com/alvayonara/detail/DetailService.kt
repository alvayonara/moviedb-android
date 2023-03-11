package com.alvayonara.detail

import com.alvayonara.detail.moviedetail.MovieDetailResponse
import com.alvayonara.detail.review.ReviewResponse
import com.alvayonara.detail.video.VideoResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DetailService {
    @GET("movie/{movieId}")
    fun getMovieDetail(@Path("movieId") movieId: Int): Observable<MovieDetailResponse>

    @GET("movie/{movieId}/reviews")
    fun getReview(@Path("movieId") movieId: Int, @Query("page") page: Int): Observable<ReviewResponse>

    @GET("movie/{movieId}/videos")
    fun getVideo(@Path("movieId") movieId: Int): Observable<VideoResponse>
}