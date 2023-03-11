package com.alvayonara.common.extension

import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.scrollToBottom() {
    val lastPosition = if (adapter != null && adapter!!.itemCount > 0) {
        adapter!!.itemCount - 1
    } else {
        0
    }
    smoothScrollToPosition(lastPosition)
}