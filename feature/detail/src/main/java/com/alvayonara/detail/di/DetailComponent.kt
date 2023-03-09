package com.alvayonara.detail.di

import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.detail.ui.DetailFragment
import com.alvayonara.detail.ui.DetailViewModel
import com.alvayonara.network.di.NetworkComponent
import com.alvayonara.network.di.NetworkInjector
import dagger.Component
import javax.inject.Scope

@DetailScope
@Component(
    dependencies = [NetworkComponent::class],
    modules = [DetailModule::class]
)
interface DetailComponent {
    companion object {
        lateinit var component: DetailComponent

        fun init() {
            component = DaggerDetailComponent.builder()
                .networkComponent(NetworkInjector.component)
                .detailModule(DetailModule())
                .build()
        }
    }

    fun inject(fragment: DetailFragment)
    fun getDetailViewModelFactory(): ViewModelFactory<DetailViewModel>
}

@Scope
@Retention
annotation class DetailScope