package com.alvayonara.home.di

import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.home.ui.HomeFragment
import com.alvayonara.home.ui.HomeViewModel
import com.alvayonara.network.di.NetworkComponent
import dagger.Component
import javax.inject.Scope

@HomeScope
@Component(
    dependencies = [NetworkComponent::class],
    modules = [HomeModule::class]
)
interface HomeComponent {
    companion object {
        lateinit var component: HomeComponent

        fun init() {
            component = DaggerHomeComponent.builder()
                .networkComponent(NetworkComponent.component)
                .homeModule(HomeModule())
                .build()
        }
    }

    fun inject(fragment: HomeFragment)
    fun getHomeViewModelFactory(): ViewModelFactory<HomeViewModel>
}

@Scope
@Retention
annotation class HomeScope