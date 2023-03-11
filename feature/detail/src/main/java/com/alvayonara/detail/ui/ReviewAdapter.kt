package com.alvayonara.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.common.extension.gone
import com.alvayonara.detail.ui.ReviewAdapter.ReviewViewHolder
import com.alvayonara.detail.ui.ReviewViews.ReviewView
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.detail.R
import com.alvayonara.moviedb_android.detail.databinding.ItemListReviewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ReviewAdapter : ListAdapter<ReviewViews, ReviewViewHolder>(ReviewDiffCallback()) {

    private var _reviewList = listOf<ReviewViews>()

    fun setReview(review: List<ReviewViews>) {
        this._reviewList = review
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder =
        ReviewViewHolder(
            ItemListReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) =
        holder.bind(getItem(position) as ReviewView)

    inner class ReviewViewHolder(
        private val binding: ItemListReviewBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: ReviewView) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_URL + data.avatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .placeholder(R.drawable.ic_avatar)
                    .into(ivReviewAvatar)
                tvReviewAuthor.text = data.author
                tvReviewRating.text = data.rating.toString()
                tvReviewContent.text = data.content
                if (absoluteAdapterPosition == _reviewList.lastIndex) {
                    viewSeparator.gone()
                }
            }
        }
    }

    class ReviewDiffCallback : DiffUtil.ItemCallback<ReviewViews>() {
        override fun areItemsTheSame(oldItem: ReviewViews, newItem: ReviewViews): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewViews, newItem: ReviewViews): Boolean {
            return oldItem == newItem
        }
    }
}