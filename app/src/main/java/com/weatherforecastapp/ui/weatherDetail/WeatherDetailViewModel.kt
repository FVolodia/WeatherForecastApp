package com.weatherforecastapp.ui.weatherDetail

import androidx.lifecycle.*
import com.weatherforecastapp.data.models.City
import com.weatherforecastapp.domain.WeatherInteractor
import com.weatherforecastapp.utils.DispatcherHolder
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class WeatherDetailViewModel(private val interactor: WeatherInteractor) : ViewModel() {

    private lateinit var city: City

    fun initCity(city: City) {
        this.city = city
    }

    fun deleteCity(city: City) {
        viewModelScope.launch { interactor.deleteCity(city) }
    }

    val cityLiveData by lazy {
        interactor.getCityFlow(city)
            .flowOn(DispatcherHolder.BG)
            .asLiveData()
    }

    val forecastLiveData by lazy {
        interactor.getForecasts(city)
            .onStart {
                try {
                    interactor.getCityForecast(city)
                } catch (t: Throwable) {

                }
            }
            .flowOn(DispatcherHolder.BG)
            .asLiveData()
    }

}