package com.alvayonara.detail.video


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("results")
    val results: List<ResultResponse>? = null
)

data class ResultResponse(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("key")
    val key: String? = null
)