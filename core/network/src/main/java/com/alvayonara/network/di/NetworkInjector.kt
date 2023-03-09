package com.alvayonara.network.di

object NetworkInjector {
    private lateinit var networkModule: NetworkModule

    lateinit var component: NetworkComponent
        private set

    fun init() {
        networkModule = NetworkModule()
        component = DaggerNetworkComponent.builder()
                .networkModule(networkModule)
                .build()
    }
}
