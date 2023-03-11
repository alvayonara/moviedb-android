package com.alvayonara.detail.ui

sealed class DetailView(
    open val id: String
) {
    object TopView : DetailView("top")

    data class ContentView(
        override val id: String,
        val title: String,
        val releaseDate: String,
        val rating: Double,
        val poster: String
    ) : DetailView(id)

    data class OverView(
        override val id: String,
        val overview: String
    ) : DetailView(id)

    data class InformationView(
        override val id: String,
        val title: String,
        val status: String,
        val language: String
    ) : DetailView(id)

    data class VideoView(
        override val id: String,
        val key: String
    ) : DetailView(id)

    data class ReviewSection(
        override val id: String,
        val reviewViews: List<ReviewViews>
    ) : DetailView(id)
}

sealed class ReviewViews(
    open val id: String
) {
    data class ReviewView(
        override val id: String,
        val avatar: String,
        val author: String,
        val rating: Double,
        val content: String
    ) : ReviewViews(id)
}