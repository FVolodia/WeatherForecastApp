package com.weatherforecastapp.extensions

import android.content.Context
import android.util.TypedValue

/**
 * Convert [dp] to pixels as int
 */
fun Context.dpToPx(dp: Float): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()

/**
 * Convert [dp] to pixels as float
 */
fun Context.dpToPxF(dp: Float): Float =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)