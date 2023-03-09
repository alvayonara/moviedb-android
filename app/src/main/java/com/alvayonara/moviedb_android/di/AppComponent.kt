package com.alvayonara.moviedb_android.di

import com.alvayonara.detail.di.DetailComponent
import com.alvayonara.home.di.HomeComponent
import com.alvayonara.movies.di.MoviesComponent
import com.alvayonara.network.di.NetworkComponent
import dagger.Component

@AppScope
@Component(
    dependencies = [
        NetworkComponent::class,
        HomeComponent::class,
        MoviesComponent::class,
        DetailComponent::class
    ]
)
interface AppComponent