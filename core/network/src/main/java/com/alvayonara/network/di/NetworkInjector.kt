package com.alvayonara.network.di

import android.app.Application

object NetworkInjector {
    private lateinit var networkModule: NetworkModule

    lateinit var component: NetworkComponent
        private set

    fun initialize(application: Application) {
        networkModule = NetworkModule(application)
        component = DaggerNetworkComponent.builder()
                .networkModule(networkModule)
                .build()
    }
}
