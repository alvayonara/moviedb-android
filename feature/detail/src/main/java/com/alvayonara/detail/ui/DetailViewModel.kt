package com.alvayonara.detail.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alvayonara.detail.DetailData
import com.alvayonara.detail.review.Review
import com.alvayonara.detail.usecase.GetMovieDetailUseCase
import com.alvayonara.detail.usecase.GetReviewUseCase
import com.alvayonara.detail.usecase.GetVideoUseCase
import io.reactivex.Observable
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

    private val _detail = MutableLiveData<DetailEvent>()
    val detail: LiveData<DetailEvent> = _detail

    fun getDetail(movieId: Int) {
        val disposable = Observable.zip(
            getMovieDetailUseCase.invoke(movieId),
            getVideoUseCase.invoke(movieId),
            getReviewUseCase.invoke(movieId, 1),
        ) { detail, video, review ->
            DetailData(detail, video, review)
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

    sealed class DetailEvent {
        object Loading : DetailEvent()
        data class Success(val data: DetailData) : DetailEvent()
        data class Failed(val data: Throwable) : DetailEvent()
    }

    override fun onCleared() {
        super.onCleared()
        _compositeDisposable.dispose()
    }
}