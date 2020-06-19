package com.weatherforecastapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import club.futurenet.android.data.db.converters.DateConverter
import com.weatherforecastapp.data.db.dao.WeatherDao
import com.weatherforecastapp.data.models.City
import com.weatherforecastapp.data.models.CityForecastsXRef
import com.weatherforecastapp.data.models.Forecast

@Database(
    entities = [
        City::class,
        Forecast::class,
        CityForecastsXRef::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverter::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {

        private const val DATABASE_NAME = "weather-android-db"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}