package com.alvayonara.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

const val BASE_DEEPLINK = "moviedb://com.alvayonara.moviedb_android/"
const val HOME_DEEPLINK = "${BASE_DEEPLINK}home"
const val MOVIES_DEEPLINK = "${BASE_DEEPLINK}movies/"
const val DETAIL_DEEPLINK = "${BASE_DEEPLINK}detail/"

fun setNavDeeplinkRequest(uri: String): NavDeepLinkRequest =
    NavDeepLinkRequest.Builder
        .fromUri(uri.toUri())
        .build()