package com.weatherforecastapp.data.models

import androidx.room.*

data class WeatherForecast(
    @Embedded
    val city: City,
    @Relation(
        parentColumn = "id",
        entityColumn = "cityId"
    )
    val forecasts: List<Forecast>
)

@Entity(
    tableName = "city_forecast_x_ref",
    primaryKeys = ["cityId", "forecastId"],
    foreignKeys = [
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
            entity = City::class,
            parentColumns = ["id"],
            childColumns = ["cityId"]
        ),
        ForeignKey(
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE,
            entity = Forecast::class,
            parentColumns = ["id"],
            childColumns = ["forecastId"]
        )
    ]
)
data class CityForecastsXRef(
    val cityId: Int,
    val forecastId: String
)