package com.alvayonara.movies.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alvayonara.common.extension.gone
import com.alvayonara.common.extension.scrollToBottom
import com.alvayonara.common.extension.visible
import com.alvayonara.common.moviedomain.Result
import com.alvayonara.common.utils.DisableItemAnimator
import com.alvayonara.common.utils.RecyclerViewEndlessScrollListener
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.moviedb_android.navigation.R.navigation
import com.alvayonara.moviedb_android.seealldata.databinding.FragmentMoviesBinding
import com.alvayonara.movies.di.MoviesComponent
import com.alvayonara.movies.ui.MoviesViewModel.MoviesEvent
import com.alvayonara.movies.ui.MoviesViewModel.MoviesState
import com.alvayonara.navigation.DeeplinkType.DETAIL
import com.alvayonara.navigation.Navigator
import com.alvayonara.navigation.generateDeeplinkUri
import javax.inject.Inject

class MoviesFragment : Fragment() {

    private var binding: FragmentMoviesBinding? = null
    private fun requireBinding() = requireNotNull(binding)

    @Inject
    lateinit var factory: ViewModelFactory<MoviesViewModel>
    private val viewModel by viewModels<MoviesViewModel> { factory }

    private lateinit var moviesAdapter: MoviesAdapter

    private val args: MoviesFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MoviesComponent.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return requireBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupView() {
        args.let { viewModel.getInitialDiscoverMovies() }
    }

    private fun setupRecyclerView() {
        requireBinding().rvMovies.apply {
            moviesAdapter = MoviesAdapter({
                (requireActivity() as Navigator).goto(
                    generateDeeplinkUri(DETAIL, it),
                    navigation.main_navigation
                )
            }, {
                scrollToBottom()
            })
            adapter = moviesAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            isNestedScrollingEnabled = false
            itemAnimator = DisableItemAnimator()
            addOnScrollListener(recyclerViewEndlessScrollListener)
        }
    }

    private val recyclerViewEndlessScrollListener = object : RecyclerViewEndlessScrollListener() {
        override fun onScrolledToVeryBottom(recyclerView: RecyclerView) {
            moviesAdapter.apply { disable() }
            viewModel.loadMore()
        }
    }

    private fun observeViewModel() {
        viewModel.movie.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesEvent.Loading -> showLoading(true)
                is MoviesEvent.Success -> {
                    showLoading(false)
                    setMoviesData(it.data)
                }
                is MoviesEvent.Failed  -> showLoading(false)
            }
        }

        viewModel.moviesState.observe(viewLifecycleOwner) {
            when (it) {
                is MoviesState.BottomLoading -> requireBinding().rvMovies.post {
                    moviesAdapter.showLoading()
                }
                is MoviesState.BottomHide    -> {
                    moviesAdapter.hideLoading()
                    recyclerViewEndlessScrollListener.enable()
                }
            }
        }
    }

    private fun setMoviesData(data: List<Result>) {
        moviesAdapter.apply {
            setMovies(data)
            isLoadMore = false
            recyclerViewEndlessScrollListener.enable()
        }
    }

    private fun showLoading(status: Boolean) {
        requireBinding().apply {
            if (status) {
                pbMovies.visible()
            } else {
                pbMovies.gone()
            }
        }
    }
}