package com.alvayonara.navigation

import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest

const val HOME_DEEPLINK = "home"
const val MOVIES_DEEPLINK = "movies/"
const val DETAIL_DEEPLINK = "detail/"

fun setNavDeeplinkRequest(url: String): NavDeepLinkRequest =
    NavDeepLinkRequest.Builder
        .fromUri(("moviedb://$url").toUri())
        .build()