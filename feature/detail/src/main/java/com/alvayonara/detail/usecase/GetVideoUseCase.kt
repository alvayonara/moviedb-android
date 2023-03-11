package com.alvayonara.detail.usecase

import com.alvayonara.detail.DetailRepository
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import com.alvayonara.detail.video.Result as VideoResult

interface GetVideoUseCase {
    fun invoke(movieId: Int): Observable<VideoResult>
}

class GetVideoUseCaseImpl @Inject constructor(
    private val detailRepository: DetailRepository
) : GetVideoUseCase {
    override fun invoke(movieId: Int): Observable<VideoResult> =
        detailRepository.getVideo(movieId)
            .map { it.results.firstOrNull() }
}