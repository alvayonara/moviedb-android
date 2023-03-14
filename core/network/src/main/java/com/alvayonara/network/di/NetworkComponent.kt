package com.alvayonara.network.di

import com.alvayonara.network.NetworkGenerator
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Component
import javax.inject.Scope

@NetworkScope
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {
    companion object {
        lateinit var component: NetworkComponent

        fun init() {
            component = DaggerNetworkComponent.builder()
                .networkModule(NetworkModule())
                .build()
        }
    }

    fun getNetworkGenerator(): NetworkGenerator
    fun getNetworkServiceWrapper(): NetworkServiceWrapper
}

@Scope
@Retention
annotation class NetworkScope