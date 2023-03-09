package com.alvayonara.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import com.alvayonara.common.utils.Event
import com.alvayonara.detail.DetailData
import com.alvayonara.detail.moviedetail.MovieDetail
import com.alvayonara.detail.review.Review
import com.alvayonara.detail.usecase.GetMovieDetailUseCase
import com.alvayonara.detail.usecase.GetReviewUseCase
import com.alvayonara.detail.usecase.GetVideoUseCase
import com.alvayonara.detail.video.Result
import com.alvayonara.detail.video.Video
import com.alvayonara.navigation.NavigationCommand
import com.alvayonara.navigation.NavigationCommand.To
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase,
    private val getReviewUseCase: GetReviewUseCase,
    private val getVideoUseCase: GetVideoUseCase
) : ViewModel() {
    private val _compositeDisposable by lazy { CompositeDisposable() }

    private val _navigation = MutableLiveData<Event<NavigationCommand>>()
    val navigation: LiveData<Event<NavigationCommand>> = _navigation

    private val _detail = MutableLiveData<DetailEvent>()
    val detail: LiveData<DetailEvent> = _detail

    private val _reviewNext = MutableLiveData<ReviewNextEvent>()
    val reviewNext: LiveData<ReviewNextEvent> = _reviewNext

    /**
     * Used to handle navigation from [ViewModel]
     */
    fun navigate(directions: NavDirections) {
        this._navigation.value = Event(NavigationCommand.To(directions))
    }

    /**
     * Used to back to previous fragment from [ViewModel]
     */
    fun navigateBack() {
        this._navigation.value = Event(NavigationCommand.Back)
    }

    fun getDetail(movieId: String) {
        val disposable = Observable.zip(
            getMovieDetailUseCase.invoke(movieId),
            getReviewUseCase.invoke(movieId, 1),
            getVideoUseCase.invoke(movieId)
        ) { detail, review, video ->
            DetailData(detail, review, video)
        }.doOnSubscribe { _detail.postValue(DetailEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _detail.postValue(DetailEvent.Success(it))
            }, {
                _detail.postValue(DetailEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    fun getReviewNext(movieId: String, page: Int) {
        val disposable = getReviewUseCase.invoke(movieId, page)
            .doOnSubscribe { _reviewNext.postValue(ReviewNextEvent.Loading) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _reviewNext.postValue(ReviewNextEvent.Success(it))
            }, {
                _reviewNext.postValue(ReviewNextEvent.Failed(it))
            })
        _compositeDisposable.add(disposable)
    }

    sealed class DetailEvent {
        object Loading : DetailEvent()
        data class Success(val data: DetailData) : DetailEvent()
        data class Failed(val data: Throwable) : DetailEvent()
    }

    sealed class ReviewNextEvent {
        object Loading : ReviewNextEvent()
        data class Success(val data: Review) : ReviewNextEvent()
        data class Failed(val data: Throwable) : ReviewNextEvent()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}