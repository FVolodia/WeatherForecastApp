package com.weatherforecastapp.di

import com.weatherforecastapp.domain.WeatherInteractor
import org.koin.dsl.module

val domainModule = module {
    factory { WeatherInteractor(get(), get()) }
}