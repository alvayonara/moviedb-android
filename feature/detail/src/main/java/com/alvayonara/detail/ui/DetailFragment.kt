package com.alvayonara.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.common.extension.gone
import com.alvayonara.common.extension.uppercaseFirstLetter
import com.alvayonara.common.extension.visible
import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.detail.di.DetailComponent
import com.alvayonara.detail.ui.DetailViewModel.DetailEvent
import com.alvayonara.moviedb_android.detail.databinding.FragmentDetailBinding
import javax.inject.Inject

class DetailFragment : Fragment() {

    private var binding: FragmentDetailBinding? = null
    private fun requireBinding() = requireNotNull(binding)

    @Inject
    lateinit var factory: ViewModelFactory<DetailViewModel>
    private val viewModel by viewModels<DetailViewModel> { factory }

    private lateinit var detailAdapter: DetailAdapter

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DetailComponent.component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return requireBinding().root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun setupView() {
        args.let { viewModel.getDetail(it.movieId) }
    }

    private fun setupRecyclerView() {
        val clickListener = object : DetailAdapter.OnClickListener {
            override fun onBack() {
                findNavController().navigateUp()
            }
        }

        detailAdapter = DetailAdapter(clickListener)
        requireBinding().rvDetail.apply {
            adapter = detailAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            itemAnimator = null
        }
    }

    private fun observeViewModel() {
        viewModel.detail.observe(viewLifecycleOwner) {
            when (it) {
                is DetailEvent.Loading -> showLoading(true)
                is DetailEvent.Success -> {
                    showLoading(false)
                    val data = it.data
                    val parser =
                        DetailViewParser(resources).parse(data.movieDetail, data.video, data.review)
                    detailAdapter.submitList(parser)
                }
                is DetailEvent.Failed  -> showLoading(false)
            }
        }
    }

    private fun showLoading(status: Boolean) {
        requireBinding().apply {
            if (status) {
                pbDetail.visible()
            } else {
                pbDetail.gone()
            }
        }
    }
}