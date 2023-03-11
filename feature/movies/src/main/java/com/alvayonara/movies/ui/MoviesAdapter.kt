package com.alvayonara.movies.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alvayonara.common.extension.invisible
import com.alvayonara.common.extension.visible
import com.alvayonara.common.utils.FORMAT_DATE
import com.alvayonara.common.utils.FORMAT_DATE_DD_MM_YYYY
import com.alvayonara.common.utils.dateTimeConvert
import com.alvayonara.moviedb_android.common.BuildConfig
import com.alvayonara.moviedb_android.seealldata.R
import com.alvayonara.moviedb_android.seealldata.databinding.ItemListLoadMoreBinding
import com.alvayonara.moviedb_android.seealldata.databinding.ItemListMoviesBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.alvayonara.common.moviedomain.Result as ResultMovie

class MoviesAdapter(
    private val clickListener: ((movieId: Int) -> Unit),
    private val scrollListener: () -> Unit
) : RecyclerView.Adapter<ViewHolder>() {

    private var _movies = ArrayList<ResultMovie?>()
    internal var isLoadMore = false

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is ResultMovie -> ViewType.MOVIES.ordinal
            null           -> ViewType.LOADING.ordinal
            else           -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.MOVIES.ordinal  -> MoviesViewHolder(
                layoutInflater.inflate(
                    R.layout.item_list_movies, parent, false
                )
            )
            ViewType.LOADING.ordinal -> LoadMoreViewHolder(
                layoutInflater.inflate(
                    R.layout.item_list_load_more, parent, false
                )
            )
            else                     -> throw IllegalArgumentException("ViewType is not recognized")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is MoviesViewHolder   -> holder.bind(getItem(position))
            is LoadMoreViewHolder -> holder.bind()
        }
    }

    inner class MoviesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemListMoviesBinding.bind(itemView)

        fun bind(data: ResultMovie?) {
            data?.let { movie ->
                binding.apply {
                    tvTitle.text = movie.title
                    tvReleaseDate.text =
                        movie.releaseDate.dateTimeConvert(FORMAT_DATE, FORMAT_DATE_DD_MM_YYYY)
                    setPoster(movie.backdropPath, ivPoster)
                    setPoster(movie.backdropPath, ivPosterInner)
                    cvUser.setOnClickListener { clickListener.invoke(movie.id) }
                }
            }
        }

        private fun setPoster(backdropPath: String, image: ImageView) {
            Glide.with(itemView.context)
                .load(BuildConfig.POSTER_URL + backdropPath)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
        }
    }

    inner class LoadMoreViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemListLoadMoreBinding.bind(itemView)

        fun bind() {
            if (isLoadMore) {
                binding.clLoading.visible()
            } else {
                binding.clLoading.invisible()
            }
        }
    }

    override fun getItemCount(): Int = _movies.size

    internal fun setMovies(movies: List<ResultMovie?>) {
        val moviesData = movies.toMutableList().apply {
            add(null)
        }
        _movies.addAll(moviesData)
        notifyDataSetChanged()
    }

    internal fun showLoading() {
        if (_movies.isEmpty()) {
            setMovies(emptyList())
        } else {
            notifyDataSetChanged()
        }
        isLoadMore = true
        scrollListener()
    }

    internal fun hideLoading() {
        isLoadMore = false
        _movies.remove(_movies.last())

        notifyItemChanged(_movies.size)
    }

    private fun getItem(position: Int): ResultMovie? {
        if (_movies.isEmpty()) {
            return null
        }
        return _movies[position]
    }

    private enum class ViewType {
        LOADING, MOVIES
    }
}