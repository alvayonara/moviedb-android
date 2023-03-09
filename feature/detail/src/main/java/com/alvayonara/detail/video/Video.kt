package com.alvayonara.detail.video

data class Video(
    val id: Int,
    val results: List<Result>
)

data class Result(
    val id: String,
    val key: String
)