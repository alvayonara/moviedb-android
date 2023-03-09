package com.alvayonara.network.di

import com.alvayonara.network.NetworkGenerator
import com.alvayonara.network.NetworkServiceWrapper
import dagger.Component

@NetworkScope
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {
    fun getNetworkGenerator(): NetworkGenerator
    fun getNetworkServiceWrapper(): NetworkServiceWrapper
}