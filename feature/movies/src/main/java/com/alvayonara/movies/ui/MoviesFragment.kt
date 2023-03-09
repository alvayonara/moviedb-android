package com.alvayonara.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.moviedb_android.seealldata.R
import com.alvayonara.movies.di.MoviesComponent
import javax.inject.Inject

class MoviesFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory<MoviesViewModel>
    private val viewModel by viewModels<MoviesViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MoviesComponent.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }
}