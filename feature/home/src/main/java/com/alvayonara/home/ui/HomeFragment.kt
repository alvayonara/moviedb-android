package com.alvayonara.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.moviedb_android.home.R
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory<HomeViewModel>
    private val viewModel by viewModels<HomeViewModel> { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeComponent.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}