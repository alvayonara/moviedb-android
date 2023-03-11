package com.alvayonara.movies.usecase

import com.alvayonara.common.moviedomain.Movie
import com.alvayonara.movies.list.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetTrendingMovieListPaginationUseCase {
    fun invoke(page: Int): Observable<Movie>
}

class GetTrendingMovieListPaginationUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetTrendingMovieListPaginationUseCase {
    override fun invoke(page: Int): Observable<Movie> =
        moviesRepository.getTrendingMovie(page)
}