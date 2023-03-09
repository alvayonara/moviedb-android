package com.alvayonara.movies.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.navigation.NavDirections
import com.alvayonara.common.moviedomain.MovieType
import com.alvayonara.movies.usecase.GetDiscoverMovieListPaginationUseCase
import com.alvayonara.movies.usecase.GetTrendingMovieListPaginationUseCase
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import com.alvayonara.common.moviedomain.Result
import com.alvayonara.common.utils.Event
import com.alvayonara.navigation.NavigationCommand
import com.alvayonara.navigation.NavigationCommand.To
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesViewModel @Inject constructor(
    private val getDiscoverMovieListPaginationUseCase: GetDiscoverMovieListPaginationUseCase,
    private val getTrendingMovieListPaginationUseCase: GetTrendingMovieListPaginationUseCase
) : ViewModel() {
    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val _movies = MutableLiveData<MoviesEvent>()
    val movie: LiveData<MoviesEvent> = _movies

    private val _movieType = MutableLiveData<MovieType>()
    val movieType: LiveData<MovieType> = _movieType

    init {
        _movieType.let { movieType ->
            if (movieType.equals(MovieType.DISCOVER)) {
                getDiscoverMovies()
            } else {
                getTrendingMovies()
            }
        }
    }

    /**
     * Used to handle navigation from [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        this._navigation.value = Event(To(directions))
    }

    /**
     * Used to back to previous fragment from [ViewModel]
     */
    fun navigateBack() {
        this._navigation.value = Event(NavigationCommand.Back)
    }

    fun setMovieType(movieType: MovieType) {
        this._movieType.value = movieType
    }

    fun getDiscoverMovies(page: Int = 1) {
        val disposable = getDiscoverMovieListPaginationUseCase.invoke(page)
            .doOnSubscribe { _movies.postValue(MoviesEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movies.postValue(MoviesEvent.Success(it))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    fun getTrendingMovies(page: Int = 1) {
        val disposable = getTrendingMovieListPaginationUseCase.invoke(page)
            .doOnSubscribe { _movies.postValue(MoviesEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _movies.postValue(MoviesEvent.Success(it))
            }, {
                _movies.postValue(MoviesEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    sealed class MoviesEvent {
        object Loading : MoviesEvent()
        data class Success(val data: List<Result>) : MoviesEvent()
        data class Failed(val data: Throwable) : MoviesEvent()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}