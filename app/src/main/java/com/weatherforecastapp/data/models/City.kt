package com.weatherforecastapp.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "cities")
@Parcelize
data class City(
    @PrimaryKey
    val id: Int,
    val name: String
) : Parcelable