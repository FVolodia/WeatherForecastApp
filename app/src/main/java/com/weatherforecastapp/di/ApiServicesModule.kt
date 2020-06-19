package com.weatherforecastapp.di

import com.weatherforecastapp.data.api.WeatherApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiServiceModules = module {
    factory<WeatherApi> {
        get<Retrofit>()
            .createNew()
    }
}

private inline fun <reified T> Retrofit.createNew(): T = create(T::class.java)