package com.weatherforecastapp

import android.app.Application
import com.facebook.stetho.Stetho

class WeatherForecastApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
    }
}