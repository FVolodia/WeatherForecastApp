package com.weatherforecastapp.extensions

import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleRes
import androidx.core.content.ContextCompat

@ColorInt
fun View.getColor(@ColorRes resId: Int) = ContextCompat.getColor(context, resId)

/**
 * Convert [dp] to pixels as int
 */
fun View.dpToPx(dp: Float): Int = context.dpToPx(dp)

/**
 * Convert [dp] to pixels as float
 */
fun View.dpToPxF(dp: Float): Float = context.dpToPxF(dp)

/**
 * Retrieve styled attribute information
 *
 * @param set The base set of attribute values
 * @param attrs The desired attributes to be retrieved
 * @param action The action will be invoked with retrieved attributes
 */
inline fun View.getAttributes(
    set: AttributeSet?,
    attrs: IntArray,
    defStyleAttr: Int = 0,
    @StyleRes defStyleRes: Int = 0,
    crossinline action: TypedArray.() -> Unit
) {
    set ?: return
    val array = context.obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    try {
        array.action()
    } finally {
        array.recycle()
    }
}