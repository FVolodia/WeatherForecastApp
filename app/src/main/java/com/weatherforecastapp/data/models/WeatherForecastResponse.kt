package com.weatherforecastapp.data.models

import java.util.*
import kotlin.random.Random

class WeatherForecastResponse(
    val city: CityResponse,
    val list: List<ForecastItem>
) {
    fun toEntity(): WeatherForecast {
        val city = City(
            id = city.id,
            name = city.name
        )
        val list = list.map {
            Forecast(
                cityId = city.id,
                date = Date(it.dt * 1000),
                main = it.weather[0].main,
                description = it.weather[0].description,
                icon = it.weather[0].iconUrl,
                clouds = it.clouds.all,
                windSpeed = it.wind.speed,
                temp = it.main.temp,
                feelsLike = it.main.feels_like,
                tempMin = it.main.temp_min,
                tempMax = it.main.temp_max,
                pressure = it.main.pressure,
                humidity = it.main.humidity
            )
        }
        return WeatherForecast(city, list)
    }
}