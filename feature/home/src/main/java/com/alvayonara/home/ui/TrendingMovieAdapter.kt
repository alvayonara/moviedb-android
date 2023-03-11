package com.alvayonara.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.home.ui.TrendingMovieAdapter.TrendingMovieViewHolder
import com.alvayonara.home.ui.TrendingViews.TrendingView
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.home.databinding.ItemListMovieBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class TrendingMovieAdapter(
    private val clickListener: HomeAdapter.OnClickListener
) : ListAdapter<TrendingViews, TrendingMovieViewHolder>(DiscoverMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMovieViewHolder =
        TrendingMovieViewHolder(
            ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: TrendingMovieViewHolder, position: Int) =
        holder.bind(getItem(position) as TrendingView)

    inner class TrendingMovieViewHolder(
        private val binding: ItemListMovieBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: TrendingView) {
            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_URL + data.poster)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.ivPoster)
            binding.tvTitle.text = data.title
            binding.cvPoster.setOnClickListener {
                clickListener.onClickMovie(data.movieId)
            }
        }
    }

    class DiscoverMovieDiffCallback : DiffUtil.ItemCallback<TrendingViews>() {
        override fun areItemsTheSame(oldItem: TrendingViews, newItem: TrendingViews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TrendingViews, newItem: TrendingViews): Boolean {
            return oldItem == newItem
        }
    }
}