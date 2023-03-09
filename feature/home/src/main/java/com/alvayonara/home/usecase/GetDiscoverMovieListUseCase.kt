package com.alvayonara.home.usecase

import com.alvayonara.home.HomeRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import com.alvayonara.common.moviedomain.Result as ResultMovie

interface GetDiscoverMovieListUseCase : () -> Observable<List<ResultMovie>>

class GetDiscoverMovieListUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
) : GetDiscoverMovieListUseCase {
    override fun invoke(): Observable<List<ResultMovie>> =
        homeRepository.getDiscoverMovie().map { it.results }
}