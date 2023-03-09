package com.alvayonara.detail.video

import com.alvayonara.common.extension.defaults

object VideoMapper {
    fun VideoResponse.mapToEntity(): Video =
        Video(
            id = this.id.defaults(0),
            results = this.results?.map { result ->
                Result(
                    id = result.id.defaults(""),
                    key = result.key.defaults("")
                )
            }.defaults(listOf())
        )
}