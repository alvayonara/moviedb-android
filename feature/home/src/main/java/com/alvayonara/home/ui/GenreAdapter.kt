package com.alvayonara.home.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.home.ui.GenreAdapter.GenreViewHolder
import com.alvayonara.home.ui.GenreViews.GenreView
import com.alvayonara.moviedb_android.home.databinding.ItemListGenreBinding

class GenreAdapter() : ListAdapter<GenreViews, GenreViewHolder>(GenreDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder =
        GenreViewHolder(
            ItemListGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) =
        holder.bind(getItem(position) as GenreView)

    inner class GenreViewHolder(
        private val binding: ItemListGenreBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: GenreView) {
            binding.tvGenre.text = data.genre
        }
    }

    class GenreDiffCallback : DiffUtil.ItemCallback<GenreViews>() {
        override fun areItemsTheSame(oldItem: GenreViews, newItem: GenreViews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GenreViews, newItem: GenreViews): Boolean {
            return oldItem == newItem
        }
    }
}