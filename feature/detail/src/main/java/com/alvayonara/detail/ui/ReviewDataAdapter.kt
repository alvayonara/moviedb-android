package com.alvayonara.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.common.extension.gone
import com.alvayonara.detail.ui.ReviewAdapter.ReviewViewHolder
import com.alvayonara.detail.ui.ReviewDataAdapter.ReviewDataViewHolder
import com.alvayonara.detail.ui.ReviewDatas.ReviewData
import com.alvayonara.detail.ui.ReviewViews.ReviewView
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailReviewBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemListReviewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class ReviewDataAdapter(
    private val clickListener: DetailAdapter.OnClickListener
) : ListAdapter<ReviewDatas, ReviewDataViewHolder>(ReviewDataMovieDiffCallback()) {

    private var _reviewList = listOf<ReviewViews>()

    fun setReview(review: List<ReviewViews>) {
        this._reviewList = review
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewDataViewHolder =
        ReviewDataViewHolder(
            ItemDetailReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ReviewDataViewHolder, position: Int) =
        holder.bind(getItem(position) as ReviewData)

    inner class ReviewDataViewHolder(
        private val binding: ItemDetailReviewBinding
    ) : ViewHolder(binding.root) {
        private val reviewAdapter = ReviewAdapter()

        init {
            binding.rvReview.apply {
                adapter = reviewAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                itemAnimator = null
            }

//            println("berapa $_reviewList")
            reviewAdapter.submitList(_reviewList)
        }

        fun bind(data: ReviewData) {
            binding.apply {
                if (data.totalPages <= 1) {
                    binding.tvShowAllReviews.gone()
                }
                binding.tvShowAllReviews.setOnClickListener { clickListener.onShowMoreReview(data.movieId) }
//                reviewAdapter.submitList(data.reviewViews)
            }
        }
    }

    class ReviewDataMovieDiffCallback : DiffUtil.ItemCallback<ReviewDatas>() {
        override fun areItemsTheSame(oldItem: ReviewDatas, newItem: ReviewDatas): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewDatas, newItem: ReviewDatas): Boolean {
            return oldItem == newItem
        }
    }
}