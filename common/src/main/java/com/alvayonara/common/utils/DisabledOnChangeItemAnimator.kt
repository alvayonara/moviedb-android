package com.alvayonara.common.utils

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class DisableItemAnimator : DefaultItemAnimator() {
    override fun animateChange(
        oldHolder: RecyclerView.ViewHolder?,
        newHolder: RecyclerView.ViewHolder?,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        if (supportsChangeAnimations) {
            return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY)
        } else {
            if (oldHolder === newHolder) {
                if (oldHolder != null) {
                    dispatchChangeFinished(oldHolder, true)
                }
            } else {
                if (oldHolder != null) {
                    dispatchChangeFinished(oldHolder, true)
                }
                if (newHolder != null) {
                    dispatchChangeFinished(newHolder, false)
                }
            }
            return false
        }
    }
}