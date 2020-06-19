package com.weatherforecastapp

import android.app.Application
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.facebook.stetho.Stetho
import com.weatherforecastapp.data.db.AppDatabase
import com.weatherforecastapp.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WeatherForecastApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        appDatabase = AppDatabase.getInstance(instance)
        startKoin {
            androidContext(this@WeatherForecastApp)
            modules(
                listOf(
                    ApiModule.module,
                    apiServiceModules,
                    viewModelModule,
                    domainModule,
                    dbModule
                )
            )
        }

        Stetho.initializeWithDefaults(this)
    }

    companion object {

        private lateinit var instance: WeatherForecastApp
        lateinit var appDatabase: AppDatabase
            private set


        fun appContext(): Application = instance

        fun getString(@StringRes resId: Int, vararg formatArgs: Any): String =
            instance.getString(resId, *formatArgs)

        fun getInteger(@IntegerRes id: Int) = instance.resources.getInteger(id)
    }
}