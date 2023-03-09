package com.alvayonara.detail

import com.alvayonara.detail.moviedetail.MovieDetail
import com.alvayonara.detail.review.Review
import com.alvayonara.detail.video.Result

data class DetailData(
    val movieDetail: MovieDetail,
    val review: Review,
    val video: Result
)
