package com.weatherforecastapp.data.models

import androidx.room.Embedded
import androidx.room.Relation


data class CurrentWeather(
    @Embedded
    val city: City,
    @Relation(
        parentColumn = "id",
        entityColumn = "cityId"
    )
    val forecast: Forecast
)