package com.alvayonara.home.usecase

import com.alvayonara.home.HomeRepository
import com.alvayonara.home.genre.GenreData
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

interface GetGenreListUseCase : () -> Observable<List<GenreData>>

class GetGenreListUseCaseImpl @Inject constructor(
    private val homeRepository: HomeRepository
): GetGenreListUseCase {
    override fun invoke(): Observable<List<GenreData>> = homeRepository.getGenre()
}