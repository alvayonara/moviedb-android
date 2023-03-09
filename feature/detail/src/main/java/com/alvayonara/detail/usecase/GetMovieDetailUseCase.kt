package com.alvayonara.detail.usecase

import com.alvayonara.detail.DetailRepository
import com.alvayonara.detail.moviedetail.MovieDetail
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface GetMovieDetailUseCase {
    fun invoke(movieId: String): Observable<MovieDetail>
}

class GetMovieDetailUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
): GetMovieDetailUseCase {
    override fun invoke(movieId: String): Observable<MovieDetail> = detailRepository.getMovieDetail(movieId)
}