package com.alvayonara.home.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alvayonara.home.HomeData
import com.alvayonara.home.usecase.GetDiscoverMovieListUseCase
import com.alvayonara.home.usecase.GetGenreListUseCase
import com.alvayonara.home.usecase.GetTrendingMovieListUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val getDiscoverMovieListUseCase: GetDiscoverMovieListUseCase,
    private val getTrendingMovieListUseCase: GetTrendingMovieListUseCase,
    private val getGenreListUseCase: GetGenreListUseCase
) : ViewModel() {
    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _home = MutableLiveData<HomeEvent>()
    val home: LiveData<HomeEvent> = _home

    init {
        getHomeData()
    }

    private fun getHomeData() {
        val disposable = Observable.zip(
            getDiscoverMovieListUseCase.invoke(),
            getTrendingMovieListUseCase.invoke(),
            getGenreListUseCase.invoke()
        ) { discover, trending, genre ->
            HomeData(discover, trending, genre)
        }.doOnSubscribe { _home.postValue(HomeEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _home.postValue(HomeEvent.Success(it))
            }, {
                _home.postValue(HomeEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    sealed class HomeEvent {
        object Loading : HomeEvent()
        data class Success(val data: HomeData) : HomeEvent()
        data class Failed(val data: Throwable) : HomeEvent()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}