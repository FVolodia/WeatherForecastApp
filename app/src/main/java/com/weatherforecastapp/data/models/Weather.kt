package com.weatherforecastapp.data.models

class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) {
    val iconUrl: String
        get() = "https://openweathermap.org/img/w/$icon.png"
}