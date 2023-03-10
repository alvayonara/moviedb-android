package com.alvayonara.navigation

import android.net.Uri
import com.alvayonara.common.utils.parseToUri
import com.alvayonara.navigation.DeeplinkType.DETAIL
import com.alvayonara.navigation.DeeplinkType.HOME
import com.alvayonara.navigation.DeeplinkType.MOVIES

private const val SCHEME = "movieDB://"
private const val HOME_DEEPLINK = "home"
private const val MOVIES_DEEPLINK = "movies/"
private const val DETAIL_DEEPLINK = "detail/"

enum class DeeplinkType {
    HOME, MOVIES, DETAIL
}

fun generateDeeplinkUri(deeplinkType: DeeplinkType, data: Any): Uri {
    val result = when (deeplinkType) {
        HOME   -> HOME_DEEPLINK
        MOVIES -> MOVIES_DEEPLINK + data
        DETAIL -> DETAIL_DEEPLINK + data
    }

    return parseToUri(SCHEME + result)
}
