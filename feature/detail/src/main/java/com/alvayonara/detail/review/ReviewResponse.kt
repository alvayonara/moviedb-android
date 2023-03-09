package com.alvayonara.detail.review


import com.google.gson.annotations.SerializedName

data class ReviewResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: List<ResultResponse>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)

data class ResultResponse(
    @SerializedName("author")
    val author: String? = null,
    @SerializedName("author_details")
    val authorDetails: AuthorDetailsResponse? = null,
    @SerializedName("content")
    val content: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("url")
    val url: String? = null
)

data class AuthorDetailsResponse(
    @SerializedName("avatar_path")
    val avatarPath: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("rating")
    val rating: Double? = null,
    @SerializedName("username")
    val username: String? = null
)