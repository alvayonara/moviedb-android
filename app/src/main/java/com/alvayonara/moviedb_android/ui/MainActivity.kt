package com.alvayonara.moviedb_android.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alvayonara.moviedb_android.R.layout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
    }
}