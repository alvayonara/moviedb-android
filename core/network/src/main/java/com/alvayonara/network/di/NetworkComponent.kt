package com.alvayonara.network.di

import android.content.Context
import com.alvayonara.network.NetworkGenerator
import com.alvayonara.network.NetworkServiceWrapper
import dagger.BindsInstance
import dagger.Component

@NetworkScope
@Component(
    modules = [NetworkModule::class]
)
interface NetworkComponent {
    fun getNetworkGenerator(): NetworkGenerator
    fun getNetworkServiceWrapper(): NetworkServiceWrapper
}