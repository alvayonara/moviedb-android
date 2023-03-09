package com.alvayonara.network

import retrofit2.Retrofit

interface NetworkGenerator : NetworkServiceWrapper {
    fun start()
}

class NetworkGeneratorImpl(
    private val retrofitBuilder: Retrofit.Builder
): NetworkGenerator {

    private lateinit var tmdbService: Retrofit

    init {
        start()
    }

    override fun start() {
        tmdbService = retrofitBuilder.build()
    }

    override fun <T> init(clazz: Class<T>): T = tmdbService.create(clazz)
}