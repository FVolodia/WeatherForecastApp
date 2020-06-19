package com.weatherforecastapp.ui.weather

import androidx.lifecycle.*
import com.weatherforecastapp.data.ResultData
import com.weatherforecastapp.domain.WeatherInteractor
import com.weatherforecastapp.utils.DispatcherHolder
import com.weatherforecastapp.utils.LocationManager
import kotlinx.coroutines.flow.flowOn

class WeatherViewModel(private val interactor: WeatherInteractor) : ViewModel() {

    val citiesLiveData by lazy {
        interactor.getCitiesFlow()
            .flowOn(DispatcherHolder.BG)
            .asLiveData()
    }

    private val _currentLocationLiveData by lazy { MutableLiveData<LocationManager.LocationData>() }

    val currentCityWeatherLiveData by lazy {
        _currentLocationLiveData.switchMap {
            liveData {
                try {
                    emit(ResultData.Success(interactor.currentWeather(it)))
                } catch (t: Throwable) {
                    emit(ResultData.Error(t))
                }
            }
        }
    }

    fun initLocation(locationData: LocationManager.LocationData) {
        _currentLocationLiveData.value = locationData
    }
}