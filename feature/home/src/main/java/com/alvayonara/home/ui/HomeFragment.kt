package com.alvayonara.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.common.extension.gone
import com.alvayonara.common.extension.visible
import com.alvayonara.common.moviedomain.MovieType
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.home.ui.HomeViewModel.HomeEvent
import com.alvayonara.moviedb_android.home.R
import com.alvayonara.moviedb_android.home.databinding.FragmentHomeBinding
import com.alvayonara.navigation.DETAIL_DEEPLINK
import com.alvayonara.navigation.MOVIES_DEEPLINK
import com.alvayonara.navigation.setNavDeeplinkRequest
import javax.inject.Inject

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    private fun requireBinding() = requireNotNull(binding)

    @Inject
    lateinit var factory: ViewModelFactory<HomeViewModel>
    private val viewModel by viewModels<HomeViewModel> { factory }

    private lateinit var homeAdapter: HomeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HomeComponent.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return requireBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        val clickListener = object : HomeAdapter.OnClickListener {
            override fun onClickMovie(movieId: String) {
//                findNavController().navigate(setNavDeeplinkRequest(DETAIL_DEEPLINK + movieId))
            }

            override fun onShowMore(movieType: MovieType) {
//                findNavController().navigate(setNavDeeplinkRequest(MOVIES_DEEPLINK + movieType))
            }
        }

        homeAdapter = HomeAdapter(clickListener)
        requireBinding().rvHome.apply {
            adapter = homeAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }
    }

    private fun observeViewModel() {
        viewModel.home.observe(viewLifecycleOwner) {
            when (it) {
                is HomeEvent.Loading -> showLoading(true)
                is HomeEvent.Success -> {
                    showLoading(false)
                    val data = it.data
                    val parser =
                        HomeViewParser(resources).parse(data.discover, data.trending, data.genre)
                    homeAdapter.submitList(parser)
                }
                is HomeEvent.Failed  -> showLoading(false)
            }
        }
    }

    private fun showLoading(status: Boolean) {
        requireBinding().apply {
            if (status) {
                pbHome.visible()
            } else {
                pbHome.gone()
            }
        }
    }
}