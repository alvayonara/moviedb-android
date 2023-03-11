package com.alvayonara.common.extension

import java.util.Locale

fun String.uppercaseFirstLetter() =
    this.lowercase(Locale.getDefault())
        .replaceFirstChar {
            it.toString()
                .uppercase(Locale.getDefault())
        }