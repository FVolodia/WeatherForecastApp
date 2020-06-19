package club.futurenet.android.data.db.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun encode(date: Date?) = date?.time

    @TypeConverter
    fun decode(value: Long?) = value?.let { Date(it) }
}