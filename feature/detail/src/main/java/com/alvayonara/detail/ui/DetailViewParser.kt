package com.alvayonara.detail.ui

import android.content.res.Resources
import com.alvayonara.detail.moviedetail.MovieDetail
import com.alvayonara.detail.review.Review
import com.alvayonara.detail.ui.DetailView.ContentView
import com.alvayonara.detail.ui.DetailView.InformationView
import com.alvayonara.detail.ui.DetailView.OverView
import com.alvayonara.detail.ui.DetailView.ReviewSection
import com.alvayonara.detail.ui.DetailView.TopView
import com.alvayonara.detail.ui.DetailView.VideoView
import com.alvayonara.detail.ui.ReviewDatas.ReviewData
import com.alvayonara.detail.ui.ReviewViews.ReviewView
import com.alvayonara.detail.video.Result
import com.alvayonara.detail.review.Result as ResultReview

class DetailViewParser(
    private val resources: Resources
) {
    fun parse(
        movieDetail: MovieDetail,
        video: Result,
        review: Review
    ): List<DetailView> {
        val result = mutableListOf<DetailView>()

        result.add(TopView)
        result.add(
            ContentView(
                id = "Content",
                title = movieDetail.title,
                releaseDate = movieDetail.releaseDate,
                rating = movieDetail.voteAverage,
                poster = movieDetail.backdropPath
            )
        )
        result.add(
            OverView(
                id = "Overview",
                overview = movieDetail.overview
            )
        )
        result.add(
            InformationView(
                id = "Information",
                title = movieDetail.title,
                status = movieDetail.status,
                language = movieDetail.originalLanguage
            )
        )

        result.add(
            VideoView(
                id = "Video",
                key = video.key
            )
        )

        if (review.totalPages != 0) {
            result.add(
                ReviewSection(
                    id = "Review",
                    review = generateReviewData(review.id, review.totalPages, review.results)
                )
            )
        }

        return result
    }

    private fun generateReviewData(
        movieId: Int,
        totalPages: Int,
        reviewResult: List<ResultReview>
    ): List<ReviewDatas> {
        val result = mutableListOf<ReviewData>()
        result.add(
            ReviewData(
                id = "Review Data",
                movieId = movieId,
                totalPages = totalPages,
                reviewViews = generateReview(reviewResult)
            )
        )

        return result
    }

    private fun generateReview(reviewList: List<ResultReview>): List<ReviewViews> {
        val result = mutableListOf<ReviewViews>()
        result.addAll(reviewList.map {
            ReviewView(
                id = it.id,
                avatar = it.authorDetails.avatarPath,
                author = it.author,
                rating = it.authorDetails.rating,
                content = it.content
            )
        })

        return result
    }
}