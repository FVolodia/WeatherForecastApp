package com.weatherforecastapp.di

import com.weatherforecastapp.WeatherForecastApp
import org.koin.dsl.module

val dbModule = module {
    single {
        WeatherForecastApp.appDatabase
    }
}