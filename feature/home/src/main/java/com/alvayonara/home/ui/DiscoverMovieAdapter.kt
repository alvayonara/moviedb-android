package com.alvayonara.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.home.ui.DiscoverMovieAdapter.DiscoverMovieViewHolder
import com.alvayonara.home.ui.DiscoverViews.DiscoverView
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.home.databinding.ItemListMovieBinding
import com.bumptech.glide.Glide

class DiscoverMovieAdapter(
    private val clickListener: HomeAdapter.OnClickListener
) : ListAdapter<DiscoverViews, DiscoverMovieViewHolder>(DiscoverMovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverMovieViewHolder =
        DiscoverMovieViewHolder(
            ItemListMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: DiscoverMovieViewHolder, position: Int) =
        holder.bind(getItem(position) as DiscoverView)

    inner class DiscoverMovieViewHolder(
        private val binding: ItemListMovieBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: DiscoverView) {
            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_URL + data.poster)
                .into(binding.ivPoster)
            binding.tvTitle.text = data.title
            binding.cvPoster.setOnClickListener {
                clickListener.onClickMovie(data.id)
            }
        }
    }

    class DiscoverMovieDiffCallback : DiffUtil.ItemCallback<DiscoverViews>() {
        override fun areItemsTheSame(oldItem: DiscoverViews, newItem: DiscoverViews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DiscoverViews, newItem: DiscoverViews): Boolean {
            return oldItem == newItem
        }
    }
}