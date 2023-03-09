package com.alvayonara.network

interface NetworkServiceWrapper {
    fun <T> init(clazz: Class<T>): T
}