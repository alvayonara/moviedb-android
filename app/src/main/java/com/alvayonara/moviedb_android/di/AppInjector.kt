package com.alvayonara.moviedb_android.di

import com.alvayonara.detail.di.DetailComponent
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.movies.di.MoviesComponent
import com.alvayonara.network.di.NetworkComponent
import com.alvayonara.network.di.NetworkInjector
import javax.inject.Scope

object AppInjector {

    private lateinit var component: AppComponent

    fun generateAppComponent() {
        setupNetworkComponent()
        setupHomeComponent()
        setupMoviesComponent()
        setupDetailComponent()

        component = DaggerAppComponent.builder()
            .networkComponent(NetworkInjector.component)
            .homeComponent(HomeComponent.component)
            .moviesComponent(MoviesComponent.component)
            .detailComponent(DetailComponent.component)
            .build()
    }

    private fun setupNetworkComponent() {
        NetworkInjector.init()
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