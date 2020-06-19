package com.weatherforecastapp.di

import com.weatherforecastapp.ui.weather.WeatherViewModel
import com.weatherforecastapp.ui.weatherDetail.WeatherDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { WeatherViewModel(get()) }
    viewModel { WeatherDetailViewModel(get()) }
}