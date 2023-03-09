package com.alvayonara.common.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory<V : ViewModel> constructor(
    private val creator: () -> V
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return creator.invoke() as T
    }
}
