package com.alvayonara.detail.review

import com.alvayonara.common.extension.defaults

object ReviewMapper {
    fun ReviewResponse.mapToEntity(): Review =
        Review(
            id = this.id.defaults(0),
            page = this.page.defaults(0),
            results = this.results?.map { result ->
                Result(
                    author = result.author.defaults("-"),
                    authorDetails = result.authorDetails?.let { author ->
                        AuthorDetails(
                            avatarPath = author.avatarPath.defaults(""),
                            name = author.name.defaults("-"),
                            rating = author.rating.defaults(0.0),
                            username = author.username.defaults("-")
                        )
                    }.defaults(AuthorDetails("", "-", 0.0, "-")),
                    content = result.content.defaults(""),
                    createdAt = result.createdAt.defaults(""),
                    id = result.id.defaults(""),
                    updatedAt = result.updatedAt.defaults(""),
                    url = result.url.defaults("")
                )
            }.defaults(listOf()),
            totalPages = this.totalPages.defaults(0),
            totalResults = this.totalResults.defaults(0)
        )
}