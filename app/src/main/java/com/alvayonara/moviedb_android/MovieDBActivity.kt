package com.alvayonara.moviedb_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvayonara.moviedb_android.R.layout

class MovieDBActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_movie_db)
    }
}