package com.alvayonara.moviedb_android.di

import com.alvayonara.detail.di.DetailComponent
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.movies.di.MoviesComponent
import com.alvayonara.network.di.NetworkComponent
import com.alvayonara.network.di.NetworkInjector
import javax.inject.Scope

object AppInjector {

    lateinit var component: AppComponent

    fun generateAppComponent() {
        val networkComponent = setupNetworkComponent()

        setupHomeComponent()
        setupMoviesComponent()
        setupDetailComponent()

        component = DaggerAppComponent.builder()
            .networkComponent(networkComponent)
            .homeComponent(HomeComponent.component)
            .moviesComponent(MoviesComponent.component)
            .detailComponent(DetailComponent.component)
            .build()
    }

    private fun setupNetworkComponent(): NetworkComponent {
        NetworkInjector.init()
        return NetworkInjector.component
    }

    private fun setupHomeComponent() {
        HomeComponent.init()
    }

    private fun setupMoviesComponent() {
        MoviesComponent.init()
    }

    private fun setupDetailComponent() {
        DetailComponent.init()
    }
}

@Scope
@Retention
annotation class AppScope