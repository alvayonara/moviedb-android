package com.alvayonara.movies.di

import com.alvayonara.common.utils.ViewModelFactory
import com.alvayonara.movies.ui.MoviesFragment
import com.alvayonara.movies.ui.MoviesViewModel
import com.alvayonara.network.di.NetworkComponent
import com.alvayonara.network.di.NetworkInjector
import dagger.Component
import javax.inject.Scope

@MoviesScope
@Component(
    dependencies = [NetworkComponent::class],
    modules = [MoviesModule::class]
)
interface MoviesComponent {
    companion object {
        lateinit var component: MoviesComponent

        fun init() {
            component = DaggerMoviesComponent.builder()
                .networkComponent(NetworkInjector.component)
                .moviesModule(MoviesModule())
                .build()
        }
    }

    fun inject(fragment: MoviesFragment)
    fun getMoviesViewModelFactory(): ViewModelFactory<MoviesViewModel>
}

@Scope
@Retention
annotation class MoviesScope