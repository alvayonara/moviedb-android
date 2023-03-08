package com.alvayonara.common.extension

import android.view.View
import androidx.core.content.ContextCompat
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.alvayonara.moviedb_android.common.R
import com.google.android.material.snackbar.Snackbar

fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun SwipeRefreshLayout.hideLoading() {
    this.isRefreshing = false
}

/**
 * @param text text of snackbar
 *
 * @return displaying Snackbar
 *
 */
fun View.showErrorSnackbar(
    text: String
) {
    val snackbar = Snackbar.make(this, text, Snackbar.LENGTH_LONG).apply {
        setBackgroundTint(ContextCompat.getColor(this.context, R.color.cinnabar))
        setTextColor(ContextCompat.getColor(this.context, R.color.white))
    }
    snackbar.show()
}