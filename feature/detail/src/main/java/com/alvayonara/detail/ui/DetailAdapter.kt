package com.alvayonara.detail.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.common.extension.gone
import com.alvayonara.common.utils.FORMAT_DATE
import com.alvayonara.common.utils.FORMAT_DATE_DD_MM_YYYY
import com.alvayonara.common.utils.dateTimeConvert
import com.alvayonara.detail.ui.DetailView.ContentView
import com.alvayonara.detail.ui.DetailView.InformationView
import com.alvayonara.detail.ui.DetailView.OverView
import com.alvayonara.detail.ui.DetailView.ReviewSection
import com.alvayonara.detail.ui.DetailView.TopView
import com.alvayonara.detail.ui.DetailView.VideoView
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailContentBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailInformationBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailOverviewBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailReviewBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailTopBinding
import com.alvayonara.moviedb_android.detail.databinding.ItemDetailVideoBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlaybackQuality
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlaybackRate
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerError
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants.PlayerState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener

class DetailAdapter(
    private val clickListener: OnClickListener
) : ListAdapter<DetailView, ViewHolder>(DetailDiffCallback()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TopView         -> ViewType.TOP_VIEW.ordinal
            is ContentView     -> ViewType.CONTENT_VIEW.ordinal
            is OverView        -> ViewType.OVERVIEW_VIEW.ordinal
            is InformationView -> ViewType.INFORMATION_VIEW.ordinal
            is VideoView       -> ViewType.VIDEO_VIEW.ordinal
            is ReviewSection   -> ViewType.REVIEW_SECTION.ordinal
            else               -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.TOP_VIEW.ordinal         -> TopViewHolder(
                ItemDetailTopBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.CONTENT_VIEW.ordinal     -> ContentViewHolder(
                ItemDetailContentBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.OVERVIEW_VIEW.ordinal    -> OverviewViewHolder(
                ItemDetailOverviewBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.INFORMATION_VIEW.ordinal -> InformationViewHolder(
                ItemDetailInformationBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.VIDEO_VIEW.ordinal       -> VideoViewHolder(
                ItemDetailVideoBinding.inflate(layoutInflater, parent, false)
            )
            ViewType.REVIEW_SECTION.ordinal   -> ReviewViewHolder(
                ItemDetailReviewBinding.inflate(layoutInflater, parent, false)
            )
            else                              -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TopViewHolder         -> holder.bind(getItem(position) as TopView)
            is ContentViewHolder     -> holder.bind(getItem(position) as ContentView)
            is OverviewViewHolder    -> holder.bind(getItem(position) as OverView)
            is InformationViewHolder -> holder.bind(getItem(position) as InformationView)
            is VideoViewHolder       -> holder.bind(getItem(position) as VideoView)
            is ReviewViewHolder      -> holder.bind(getItem(position) as ReviewSection)
        }
    }

    inner class TopViewHolder(
        private val binding: ItemDetailTopBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: TopView) {
            binding.ivBack.setOnClickListener { clickListener.onBack() }
        }
    }

    inner class ContentViewHolder(
        private val binding: ItemDetailContentBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: ContentView) {
            binding.apply {
                tvTitle.text = data.title
                tvReleaseDate.text =
                    data.releaseDate.dateTimeConvert(FORMAT_DATE, FORMAT_DATE_DD_MM_YYYY)
                tvRating.text = data.rating.toString()
                Glide.with(itemView.context)
                    .load(BuildConfig.POSTER_URL + data.poster)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(binding.ivPoster)
            }
        }
    }

    inner class OverviewViewHolder(
        private val binding: ItemDetailOverviewBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: OverView) {
            binding.tvOverview.text = data.overview
        }
    }

    inner class InformationViewHolder(
        private val binding: ItemDetailInformationBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: InformationView) {
            binding.apply {
                tvTitle.text = data.title
                tvStatus.text = data.status
                tvLanguage.text = data.language
            }
        }
    }

    inner class VideoViewHolder(
        private val binding: ItemDetailVideoBinding
    ) : ViewHolder(binding.root) {
        fun bind(data: VideoView) {
            binding.youtubePlayerView.addYouTubePlayerListener(object : YouTubePlayerListener {
                override fun onApiChange(youTubePlayer: YouTubePlayer) {}

                override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {}

                override fun onError(youTubePlayer: YouTubePlayer, error: PlayerError) {}

                override fun onPlaybackQualityChange(
                    youTubePlayer: YouTubePlayer,
                    playbackQuality: PlaybackQuality
                ) {
                }

                override fun onPlaybackRateChange(
                    youTubePlayer: YouTubePlayer,
                    playbackRate: PlaybackRate
                ) {
                }

                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.cueVideo(data.key, 0F)
                }

                override fun onStateChange(youTubePlayer: YouTubePlayer, state: PlayerState) {}

                override fun onVideoDuration(youTubePlayer: YouTubePlayer, duration: Float) {}

                override fun onVideoId(youTubePlayer: YouTubePlayer, videoId: String) {}

                override fun onVideoLoadedFraction(
                    youTubePlayer: YouTubePlayer,
                    loadedFraction: Float
                ) {
                }
            })
        }
    }

    inner class ReviewViewHolder(
        private val binding: ItemDetailReviewBinding
    ) : ViewHolder(binding.root) {
        private val reviewAdapter = ReviewAdapter()

        init {
            binding.rvReview.apply {
                adapter = reviewAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                itemAnimator = null
            }
        }

        fun bind(data: ReviewSection) {
            reviewAdapter.setReview(data.reviewViews)
            reviewAdapter.submitList(data.reviewViews)
        }
    }

    class DetailDiffCallback : DiffUtil.ItemCallback<DetailView>() {
        override fun areItemsTheSame(oldItem: DetailView, newItem: DetailView): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DetailView, newItem: DetailView): Boolean {
            return oldItem == newItem
        }
    }

    private enum class ViewType {
        TOP_VIEW, CONTENT_VIEW, OVERVIEW_VIEW, INFORMATION_VIEW, VIDEO_VIEW, REVIEW_SECTION
    }

    interface OnClickListener {
        fun onBack()
    }
}