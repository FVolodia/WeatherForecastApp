package com.weatherforecastapp.data

sealed class ResultData<out T> {
    data class Success<T>(val data: T) : ResultData<T>()
    data class Error(val error: Throwable) : ResultData<Nothing>()
}