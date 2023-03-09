package com.alvayonara.home.usecase

import com.alvayonara.home.HomeRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import com.alvayonara.common.moviedomain.Result as ResultMovie

interface GetTrendingMovieListUseCase : () -> Observable<List<ResultMovie>>

class GetTrendingMovieListUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : GetTrendingMovieListUseCase {
    override fun invoke(): Observable<List<ResultMovie>> =
        homeRepository.getTrendingMovie().map { it.results }
}