package com.weatherforecastapp.data.db.dao

import androidx.room.*
import com.weatherforecastapp.data.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Transaction
    @Query("SELECT * FROM cities ORDER BY name ASC")
    fun getCitiesFlow(): Flow<List<CurrentWeather>>

    //CITY
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(city: City): Long

    @Update
    suspend fun update(city: City)

    @Transaction
    suspend fun insertOrUpdateCities(cities: List<City>) {
        cities.forEach {
            if (insert(it) == -1L) update(it)
        }
    }

    @Delete
    suspend fun deleteCity(city: City)

    //Forecast
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(forecast: Forecast): Long

    @Update
    suspend fun update(forecast: Forecast)

    @Transaction
    suspend fun insertOrUpdateForecasts(forecasts: List<Forecast>) {
        forecasts.forEach {
            if (insert(it) == -1L) update(it)
        }
    }

    @Transaction
    @Query("SELECT * FROM cities WHERE id=:cityId")
    fun getCityFlow(cityId: Int): Flow<CurrentWeather>

    @Transaction
    @Query("SELECT * FROM forecasts JOIN city_forecast_x_ref on forecasts.id=city_forecast_x_ref.forecastId WHERE city_forecast_x_ref.cityId=:cityId")
    fun getForecastFlow(cityId: Int): Flow<List<Forecast>>

    @Transaction
    @Query("SELECT * FROM forecasts JOIN city_forecast_x_ref on forecasts.id=city_forecast_x_ref.forecastId WHERE city_forecast_x_ref.cityId=:cityId")
    fun getForecastList(cityId: Int): List<Forecast>

    @Transaction
    suspend fun deleteForecastForCity(cityId: Int) {
        val list = getForecastList(cityId)
        deleteForecast(cityId, list)
    }

    @Query("DELETE from forecasts WHERE cityId=:cityId IN (:list)")
    fun deleteForecast(cityId: Int, list: List<Forecast>)


    //CityForecast Ref
    @Transaction
    suspend fun insertOrCityForecastRef(refs: List<CityForecastsXRef>) {
        refs.forEach {
            if (insertRef(it) == -1L) updateRef(it)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRef(ref: CityForecastsXRef): Long

    @Update
    suspend fun updateRef(ref: CityForecastsXRef)

}