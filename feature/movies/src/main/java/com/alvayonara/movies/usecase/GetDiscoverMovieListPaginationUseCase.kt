package com.alvayonara.movies.usecase

import com.alvayonara.common.moviedomain.Movie
import com.alvayonara.movies.list.MoviesRepository
import io.reactivex.Observable
import javax.inject.Inject

interface GetDiscoverMovieListPaginationUseCase {
    fun invoke(page: Int): Observable<Movie>
}

class GetDiscoverMovieListPaginationUseCaseImpl @Inject constructor(
    private val moviesRepository: MoviesRepository
) : GetDiscoverMovieListPaginationUseCase {
    override fun invoke(page: Int): Observable<Movie> =
        moviesRepository.getDiscoverMovie(page)
}