package com.weatherforecastapp.data.api

import com.weatherforecastapp.data.models.CurrentWeatherResponse
import com.weatherforecastapp.data.models.WeatherForecastResponse
import retrofit2.http.*

interface WeatherApi {

    @GET("forecast")
    suspend fun cityDetails(@QueryMap map: Map<String, String>): WeatherForecastResponse

    @GET("weather")
    suspend fun fetchCity(@QueryMap map: Map<String, String>): CurrentWeatherResponse
}