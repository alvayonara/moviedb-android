package com.alvayonara.moviedb_android

import android.app.Application
import com.alvayonara.moviedb_android.di.AppInjector

class MovieDBApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppInjector.generateAppComponent()
    }
}