package com.alvayonara.detail.di

import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.common.extension.factory
import com.alvayonara.detail.DetailRepository
import com.alvayonara.detail.DetailRepositoryImpl
import com.alvayonara.detail.DetailService
import com.alvayonara.detail.ui.DetailViewModel
import com.alvayonara.detail.usecase.GetMovieDetailUseCase
import com.alvayonara.detail.usecase.GetMovieDetailUseCaseImpl
import com.alvayonara.detail.usecase.GetReviewUseCase
import com.alvayonara.detail.usecase.GetReviewUseCaseImpl
import com.alvayonara.detail.usecase.GetVideoUseCase
import com.alvayonara.detail.usecase.GetVideoUseCaseImpl
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Module
import dagger.Provides
import dagger.Lazy

@Module
class DetailModule {
    @Provides
    fun provideDetailService(networkServiceWrapper: NetworkServiceWrapper): DetailService {
        return networkServiceWrapper.init(
            DetailService::class.java
        )
    }

    @Provides
    internal fun provideDetailRepository(
        detailService: DetailService
    ): DetailRepository = DetailRepositoryImpl(detailService)

    @Provides
    internal fun provideMovieDetailUseCase(
        detailRepository: DetailRepository
    ): GetMovieDetailUseCase = GetMovieDetailUseCaseImpl(detailRepository)

    @Provides
    internal fun provideReviewUseCase(
        detailRepository: DetailRepository
    ): GetReviewUseCase = GetReviewUseCaseImpl(detailRepository)

    @Provides
    internal fun provideVideoUseCase(
        detailRepository: DetailRepository
    ): GetVideoUseCase = GetVideoUseCaseImpl(detailRepository)

    @Provides
    fun provideDetailViewModelFactory(
        getMovieDetailUseCase: Lazy<GetMovieDetailUseCase>,
        getReviewUseCase: Lazy<GetReviewUseCase>,
        getVideoUseCase: Lazy<GetVideoUseCase>
    ): ViewModelFactory<DetailViewModel> = factory {
        DetailViewModel(
            getMovieDetailUseCase.get(),
            getReviewUseCase.get(),
            getVideoUseCase.get()
        )
    }
}