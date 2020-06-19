package com.weatherforecastapp.utils

import java.util.*

fun Float.toCelsius(): String {
    val c = this - 273.15
    return String.format(Locale.US, "%+.0f°C", c)
}

fun Float.toSpeed(): String {
    return String.format(Locale.US, "%.1f m/s", this)
}

fun Int.toMmHg(): String {
    val p = this * 0.75f
    return String.format(Locale.US, "%.0f", p)
}

fun Int.toPercent(): String {
    return String.format(Locale.US, "%d%%", this)
}