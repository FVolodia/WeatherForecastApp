package com.weatherforecastapp.di

import com.google.gson.GsonBuilder
import com.weatherforecastapp.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiModule {

    const val WEATHER = BuildConfig.WEATHER_ENDPOINT

    val module = module {

        single<Retrofit>() {
            get<Retrofit.Builder>()
                .baseUrl(WEATHER)
                .build()
        }

        single<Retrofit.Builder> {
            Retrofit.Builder()
                .client(get<OkHttpClient>())
                .addConverterFactory(get<GsonConverterFactory>())
        }

        single {
            OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .apply {
                    if (BuildConfig.DEBUG) {
                        addInterceptor(
                            HttpLoggingInterceptor().apply {
                                level = HttpLoggingInterceptor.Level.BODY
                            }
                        )
                    }
                }.build()
        }

        factory<GsonConverterFactory> {
            GsonConverterFactory.create(
                GsonBuilder()
                    .create()
            )
        }
    }
}