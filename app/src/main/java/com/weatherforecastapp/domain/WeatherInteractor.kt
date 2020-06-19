package com.weatherforecastapp.domain

import android.util.Log
import com.weatherforecastapp.data.api.WeatherApi
import com.weatherforecastapp.data.db.AppDatabase
import com.weatherforecastapp.data.models.City
import com.weatherforecastapp.data.models.CityForecastsXRef
import com.weatherforecastapp.data.models.CurrentWeather
import com.weatherforecastapp.utils.LocationManager
import com.weatherforecastapp.utils.onBG
import kotlinx.coroutines.flow.distinctUntilChanged

class WeatherInteractor(private val api: WeatherApi, private val db: AppDatabase) {

    private val apiKey = "cbb27ac645e4592c5a70f28532c2dac2"

    private val weatherDao by lazy { db.weatherDao() }

    fun getCitiesFlow() = weatherDao.getCitiesFlow().distinctUntilChanged()
    fun getCityFlow(city: City) = weatherDao.getCityFlow(city.id).distinctUntilChanged()
    fun getForecasts(city: City) = weatherDao.getForecastFlow(city.id).distinctUntilChanged()

    suspend fun currentWeather(point: LocationManager.LocationData): CurrentWeather = onBG {
        val query = mapOf(
            "lat" to point.latitude.toString(),
            "lon" to point.longitude.toString(),
            "appid" to apiKey
        )
        val currentWeather = api.fetchCity(query).toEntity()
        weatherDao.insertOrUpdateCities(listOf(currentWeather.city))
        weatherDao.insertOrUpdateForecasts(listOf(currentWeather.forecast))
        currentWeather
    }

    suspend fun getCityForecast(city: City) = onBG {
        val query = mapOf(
            "id" to "${city.id}",
            "appid" to apiKey
        )
        val forecast = api.cityDetails(query).toEntity()
        //delete all for current city
        weatherDao.deleteForecastForCity(city.id)
        Log.d("WeatherInteractor", "getCityForecast: ${forecast.forecasts.size}")
        weatherDao.insertOrUpdateForecasts(forecast.forecasts)

        val refs =
            forecast.forecasts.map { CityForecastsXRef(cityId = city.id, forecastId = it.id) }
        weatherDao.insertOrCityForecastRef(refs)
    }

    suspend fun deleteCity(city: City) = onBG {
        weatherDao.deleteCity(city)
    }
}