package com.alvayonara.movies.usecase

import com.alvayonara.movies.list.MoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import com.alvayonara.common.moviedomain.Result as ResultMovie

interface GetDiscoverMovieListPaginationUseCase {
    fun invoke(page: Int): Observable<List<ResultMovie>>
}

class GetDiscoverMovieListPaginationUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetDiscoverMovieListPaginationUseCase {
    override fun invoke(page: Int): Observable<List<ResultMovie>> =
        moviesRepository.getDiscoverMovie(page).map { it.results }
}