package com.alvayonara.detail.usecase

import com.alvayonara.detail.DetailRepository
import com.alvayonara.detail.review.Review
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface GetReviewUseCase {
    fun invoke(movieId: String, page: Int): Observable<Review>
}

class GetReviewUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
) : GetReviewUseCase {
    override fun invoke(movieId: String, page: Int): Observable<Review> =
        detailRepository.getReview(movieId, page)
}