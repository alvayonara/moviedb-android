package com.alvayonara.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alvayonara.common.moviedomain.MovieType
import com.alvayonara.common.moviedomain.MovieType.DISCOVER
import com.alvayonara.common.moviedomain.Result
import com.alvayonara.movies.usecase.GetDiscoverMovieListPaginationUseCase
import com.alvayonara.movies.usecase.GetTrendingMovieListPaginationUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val getDiscoverMovieListPaginationUseCase: GetDiscoverMovieListPaginationUseCase,
    private val getTrendingMovieListPaginationUseCase: GetTrendingMovieListPaginationUseCase
) : ViewModel() {
    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _movies = MutableLiveData<MoviesEvent>()
    val movie: LiveData<MoviesEvent> = _movies

    private val _movieState = MutableLiveData<MoviesState>()
    val moviesState: LiveData<MoviesState> = _movieState

    private var _currentPage: Int = 1
    private var _movieType: MovieType? = null

    fun setMovieType(movieType: MovieType) {
        this._movieType = movieType
        getMovies()
    }

    private fun getInitialDiscoverMovies(page: Int = _currentPage) {
        val disposable = getDiscoverMovieListPaginationUseCase.invoke(page)
            .doOnSubscribe { _movies.postValue(MoviesEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movies.postValue(MoviesEvent.Success(it.results))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    private fun getInitialTrendingMovies(page: Int = _currentPage) {
        val disposable = getTrendingMovieListPaginationUseCase.invoke(page)
            .doOnSubscribe { _movies.postValue(MoviesEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movies.postValue(MoviesEvent.Success(it.results))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    private fun getNextDiscoverMovies(page: Int = _currentPage + 1) {
        val disposable = getDiscoverMovieListPaginationUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _currentPage = it.page
                _movieState.value = MoviesState.BottomHide
                _movies.postValue(MoviesEvent.Success(it.results))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    private fun getNextTrendingMovies(page: Int = _currentPage + 1) {
        val disposable = getTrendingMovieListPaginationUseCase.invoke(page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _currentPage = it.page
                _movies.postValue(MoviesEvent.Success(it.results))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
        _movieState.value = MoviesState.BottomHide
    }

    fun loadMore() {
        _movieState.value = MoviesState.BottomLoading
        getMovies({ getNextDiscoverMovies() }, { getNextTrendingMovies() })
    }

    private fun getMovies(
        discover: () -> Unit = { getInitialDiscoverMovies() },
        trending: () -> Unit = { getInitialTrendingMovies() }
    ) {
        if (_movieType == DISCOVER) {
            discover()
        } else {
            trending()
        }
    }

    sealed class MoviesEvent {
        object Loading : MoviesEvent()
        data class Success(val data: List<Result>) : MoviesEvent()
        data class Failed(val data: Throwable) : MoviesEvent()
    }

    sealed class MoviesState {
        object BottomLoading : MoviesState()
        object BottomHide : MoviesState()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}