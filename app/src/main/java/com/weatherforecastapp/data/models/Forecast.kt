package com.weatherforecastapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.weatherforecastapp.utils.toCelsius
import java.util.*

@Entity(tableName = "forecasts")
data class Forecast(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val cityId: Int,
    val date: Date,
    val main: String,
    val description: String,
    val icon: String,
    val clouds: Int,
    val windSpeed: Float,
    val temp: Float,
    val feelsLike: Float,
    val tempMin: Float,
    val tempMax: Float,
    val pressure: Int,
    val humidity: Int
) {
    val tempMinMax: String
        get() = "${tempMin.toCelsius()} / ${tempMax.toCelsius()}"
}