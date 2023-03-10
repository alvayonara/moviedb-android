package com.alvayonara.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavDeepLinkRequest
import com.alvayonara.navigation.DeeplinkType.HOME

const val HOME_DEEPLINK = "home"
const val MOVIES_DEEPLINK = "movies"
const val DETAIL_DEEPLINK = "detail"

enum class DeeplinkType {
    HOME, MOVIES, DETAIL
}

fun setNavDeeplinkRequest(deeplinkType: DeeplinkType, data: String): NavDeepLinkRequest {
    val uri = Uri.Builder()
        .scheme("myApp")
        .authority("com.alvayonara")

    if (deeplinkType != HOME) {
        uri.path(data)
    }

    return NavDeepLinkRequest.Builder
        .fromUri(uri.build())
        .build()
}
