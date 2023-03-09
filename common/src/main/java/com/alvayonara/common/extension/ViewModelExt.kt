package com.alvayonara.common.extension

import androidx.lifecycle.ViewModel
import com.alvayonara.common.utils.ViewModelFactory

inline fun <reified T : ViewModel> factory(noinline creator: () -> T) = ViewModelFactory(creator)
