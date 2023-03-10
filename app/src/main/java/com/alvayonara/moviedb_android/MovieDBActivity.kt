package com.alvayonara.moviedb_android

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.alvayonara.moviedb_android.R.layout
import com.alvayonara.navigation.Navigator

class MovieDBActivity : AppCompatActivity(), Navigator {
    override lateinit var navHostFragment: NavHostFragment
    override lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_movie_db)

        navController = findNavController(R.id.fragmentContainerView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
    }

    override fun goto(uri: Uri) {
        val request = NavDeepLinkRequest.Builder
            .fromUri(uri)
            .build()
        navController.navigate(request)
    }
}