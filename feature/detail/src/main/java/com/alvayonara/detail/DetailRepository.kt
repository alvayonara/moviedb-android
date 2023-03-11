package com.alvayonara.detail

import com.alvayonara.detail.moviedetail.MovieDetail
import com.alvayonara.detail.moviedetail.MovieDetailMapper.mapToEntity
import com.alvayonara.detail.review.Review
import com.alvayonara.detail.review.ReviewMapper.mapToEntity
import com.alvayonara.detail.video.Video
import com.alvayonara.detail.video.VideoMapper.mapToEntity
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface DetailRepository {
    fun getMovieDetail(movieId: Int): Observable<MovieDetail>
    fun getReview(movieId: Int, page: Int): Observable<Review>
    fun getVideo(movieId: Int): Observable<Video>
}

class DetailRepositoryImpl @Inject constructor(
    private val detailService: DetailService
) : DetailRepository {
    override fun getMovieDetail(movieId: Int): Observable<MovieDetail> =
        detailService.getMovieDetail(movieId).map { it.mapToEntity() }

    override fun getReview(movieId: Int, page: Int): Observable<Review> =
        detailService.getReview(movieId, page).map { it.mapToEntity() }

    override fun getVideo(movieId: Int): Observable<Video> = detailService.getVideo(movieId).map { it.mapToEntity() }
}
