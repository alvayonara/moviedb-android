package com.alvayonara.movies.usecase

import com.alvayonara.movies.list.MoviesRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import com.alvayonara.common.moviedomain.Result as ResultMovie

interface GetTrendingMovieListPaginationUseCase {
    fun invoke(page: Int): Observable<List<ResultMovie>>
}

class GetTrendingMovieListPaginationUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetTrendingMovieListPaginationUseCase {
    override fun invoke(page: Int): Observable<List<ResultMovie>> =
        moviesRepository.getTrendingMovie(page).map { it.results }
}