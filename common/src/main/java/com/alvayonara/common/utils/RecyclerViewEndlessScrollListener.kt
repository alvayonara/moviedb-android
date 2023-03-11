package com.alvayonara.common.utils

import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewEndlessScrollListener : RecyclerView.OnScrollListener() {
    private var _isEnabled = true

    fun disable() {
        synchronized(_isEnabled) {
            _isEnabled = false
        }
    }

    fun enable() {
        synchronized(_isEnabled) {
            _isEnabled = true
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (!recyclerView.canScrollVertically(1) && _isEnabled) {
            onScrolledToVeryBottom(recyclerView)
        }
    }

    abstract fun onScrolledToVeryBottom(recyclerView: RecyclerView)
}
