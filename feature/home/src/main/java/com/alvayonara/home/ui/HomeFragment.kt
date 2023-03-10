package com.alvayonara.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.home.ui.HomeViewModel.HomeEvent
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {

    }

    private fun observeViewModel() {
        viewModel.home.observe(viewLifecycleOwner) {
            when(it) {
                is HomeEvent.Loading -> Unit
                is HomeEvent.Success -> {
                    val data = it.data
                    val parser = HomeViewParser(resources).parse(data.discover, data.trending, data.genre)

                }
                is HomeEvent.Failed -> Unit
            }
        }
    }

}