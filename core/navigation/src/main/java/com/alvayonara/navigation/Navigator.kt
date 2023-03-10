package com.alvayonara.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

interface Navigator {
    var navHostFragment: NavHostFragment
    var navController: NavController
    fun goto(uri: Uri)

    fun goto(uri: Uri, graphId: Int) {
        val myNavHostFragment: NavHostFragment = navHostFragment
        val inflater = myNavHostFragment.navController.navInflater
        val graph = inflater.inflate(graphId)
        myNavHostFragment.navController.graph = graph
        goto(uri)
    }
}