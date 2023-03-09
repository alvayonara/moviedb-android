package com.alvayonara.movies.di

import com.alvayonara.common.extension.factory
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.movies.usecase.GetDiscoverMovieListPaginationUseCase
import com.alvayonara.movies.usecase.GetDiscoverMovieListPaginationUseCaseImpl
import com.alvayonara.movies.usecase.GetTrendingMovieListPaginationUseCase
import com.alvayonara.movies.usecase.GetTrendingMovieListPaginationUseCaseImpl
import com.alvayonara.movies.list.MoviesRepository
import com.alvayonara.movies.list.MoviesRepositoryImpl
import com.alvayonara.movies.list.MoviesService
import com.alvayonara.movies.ui.MoviesViewModel
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
class MoviesModule {
    @Provides
    fun provideMoviesService(networkServiceWrapper: NetworkServiceWrapper): MoviesService {
        return networkServiceWrapper.init(
            MoviesService::class.java
        )
    }

    @Provides
    internal fun provideMoviesRepository(
        moviesService: MoviesService
    ): MoviesRepository = MoviesRepositoryImpl(moviesService)

    @Provides
    internal fun provideDetailMovieListPaginationUseCase(
        moviesRepository: MoviesRepository
    ): GetDiscoverMovieListPaginationUseCase =
        GetDiscoverMovieListPaginationUseCaseImpl(moviesRepository)

    @Provides
    internal fun provideReviewUseCase(
        moviesRepository: MoviesRepository
    ): GetTrendingMovieListPaginationUseCase =
        GetTrendingMovieListPaginationUseCaseImpl(moviesRepository)

    @Provides
    fun provideMoviesViewModelFactory(
        getDiscoverMovieListPaginationUseCase: Lazy<GetDiscoverMovieListPaginationUseCase>,
        getTrendingMovieListPaginationUseCase: Lazy<GetTrendingMovieListPaginationUseCase>,
    ): ViewModelFactory<MoviesViewModel> = factory {
        MoviesViewModel(
            getDiscoverMovieListPaginationUseCase.get(),
            getTrendingMovieListPaginationUseCase.get()
        )
    }
}