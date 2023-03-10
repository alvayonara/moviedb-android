package com.alvayonara.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.common.moviedomain.MovieType
import com.alvayonara.home.ui.HomeView.DiscoverSection
import com.alvayonara.home.ui.HomeView.GenreSection
import com.alvayonara.home.ui.HomeView.TopView
import com.alvayonara.home.ui.HomeView.TrendingSection
import com.alvayonara.moviedb_android.home.databinding.ItemListTopBinding
import com.alvayonara.moviedb_android.home.databinding.LayoutGenreBinding
import com.alvayonara.moviedb_android.home.databinding.LayoutHomeMovieBinding

class HomeAdapter(
    private val clickListener: OnClickListener
) : ListAdapter<HomeView, ViewHolder>(HomeDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TopView         -> ViewType.TOP_VIEW.ordinal
            is GenreSection    -> ViewType.GENRE_SECTION.ordinal
            is DiscoverSection -> ViewType.DISCOVER_SECTION.ordinal
            is TrendingSection -> ViewType.TRENDING_SECTION.ordinal
            else               -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.TOP_VIEW.ordinal         -> TopViewHolder(
                ItemListTopBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.GENRE_SECTION.ordinal    -> GenreViewHolder(
                LayoutGenreBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.DISCOVER_SECTION.ordinal -> DiscoverViewHolder(
                LayoutHomeMovieBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.TRENDING_SECTION.ordinal -> TrendingViewHolder(
                LayoutHomeMovieBinding.inflate(layoutInflater, parent, false)
            )
            else                              -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        when (holder) {
            is GenreViewHolder    -> holder.bind(getItem(position) as GenreSection)
            is DiscoverViewHolder -> holder.bind(getItem(position) as DiscoverSection)
            is TrendingViewHolder -> holder.bind(getItem(position) as TrendingSection)
        }
    }

    class TopViewHolder(
        private val binding: ItemListTopBinding
    ) : ViewHolder(binding.root)

    inner class GenreViewHolder(
        private val binding: LayoutGenreBinding
    ) : ViewHolder(binding.root) {

        private val genreAdapter = GenreAdapter(clickListener)

        init {
            binding.rvGenre.apply {
                adapter = genreAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                itemAnimator = null
            }
        }

        fun bind(data: GenreSection) {
            println("genres->: ${data}")
            genreAdapter.submitList(data.genreViews)
        }
    }

    inner class DiscoverViewHolder(
        private val binding: LayoutHomeMovieBinding
    ) : ViewHolder(binding.root) {

        private val discoverMovieAdapter = DiscoverMovieAdapter(clickListener)

        init {
            binding.rvMovie.apply {
                adapter = discoverMovieAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                itemAnimator = null
            }
        }

        fun bind(data: DiscoverSection) {
            binding.layoutHeader.apply {
                tvHeaderTitle.text = data.title
                tvHeaderSubtitle.text = data.subtitle
                tvShowMore.setOnClickListener { clickListener.onShowMore(data.movieType) }
            }
            discoverMovieAdapter.submitList(data.discoverViews)
        }
    }

    inner class TrendingViewHolder(
        private val binding: LayoutHomeMovieBinding
    ) : ViewHolder(binding.root) {

        private val trendingMovieAdapter = TrendingMovieAdapter(clickListener)

        init {
            binding.rvMovie.apply {
                adapter = trendingMovieAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                itemAnimator = null
            }
        }

        fun bind(data: TrendingSection) {
            binding.layoutHeader.apply {
                tvHeaderTitle.text = data.title
                tvHeaderSubtitle.text = data.subtitle
                tvShowMore.setOnClickListener { clickListener.onShowMore(data.movieType) }
            }
            trendingMovieAdapter.submitList(data.trendingViews)
        }
    }

    class HomeDiffCallback : DiffUtil.ItemCallback<HomeView>() {
        override fun areItemsTheSame(oldItem: HomeView, newItem: HomeView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HomeView, newItem: HomeView): Boolean {
            return oldItem == newItem
        }
    }

    private enum class ViewType {
        TOP_VIEW, GENRE_SECTION, DISCOVER_SECTION, TRENDING_SECTION
    }

    interface OnClickListener {
        fun onClickMovie(movieId: String)
        fun onShowMore(movieType: MovieType)
    }
}