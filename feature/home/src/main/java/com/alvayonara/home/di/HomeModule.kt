package com.alvayonara.home.di

import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.common.extension.factory
import com.alvayonara.home.usecase.GetDiscoverMovieListUseCase
import com.alvayonara.home.usecase.GetDiscoverMovieListUseCaseImpl
import com.alvayonara.home.usecase.GetGenreListUseCase
import com.alvayonara.home.usecase.GetGenreListUseCaseImpl
import com.alvayonara.home.usecase.GetTrendingMovieListUseCase
import com.alvayonara.home.usecase.GetTrendingMovieListUseCaseImpl
import com.alvayonara.home.HomeRepository
import com.alvayonara.home.HomeRepositoryImpl
import com.alvayonara.home.HomeService
import com.alvayonara.home.ui.HomeViewModel
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
class HomeModule {
    @Provides
    fun provideHomeService(networkServiceWrapper: NetworkServiceWrapper): HomeService {
        return networkServiceWrapper.init(
            HomeService::class.java
        )
    }

    @Provides
    internal fun provideHomeRepository(
        homeService: HomeService
    ): HomeRepository = HomeRepositoryImpl(homeService)

    @Provides
    internal fun provideDiscoverMovieUseCase(
        homeRepository: HomeRepository
    ): GetDiscoverMovieListUseCase = GetDiscoverMovieListUseCaseImpl(homeRepository)

    @Provides
    internal fun provideTrendingMovieUseCase(
        homeRepository: HomeRepository
    ): GetTrendingMovieListUseCase = GetTrendingMovieListUseCaseImpl(homeRepository)

    @Provides
    internal fun provideGenreListUseCase(
        homeRepository: HomeRepository
    ): GetGenreListUseCase = GetGenreListUseCaseImpl(homeRepository)

    @Provides
    fun provideHomeViewModelFactory(
        getDiscoverMovieListUseCase: Lazy<GetDiscoverMovieListUseCase>,
        getTrendingMovieListUseCase: Lazy<GetTrendingMovieListUseCase>,
        getGenreListUseCase: Lazy<GetGenreListUseCase>
    ): ViewModelFactory<HomeViewModel> = factory {
        HomeViewModel(
            getDiscoverMovieListUseCase.get(),
            getTrendingMovieListUseCase.get(),
            getGenreListUseCase.get()
        )
    }
}