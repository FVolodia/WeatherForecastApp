package com.weatherforecastapp.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Observe the current [LiveData] by the [observer]
 */
fun <T> LiveData<T>.observeX(fragment: Fragment, observer: (T) -> Unit) {
    val owner = fragment.viewLifecycleOwner
    observe(owner, Observer {
        if (it != null) observer(it)
    })
}