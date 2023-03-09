package com.alvayonara.common.extension

fun <T> T?.defaults(default: T): T = this ?: default