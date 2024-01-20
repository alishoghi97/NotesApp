package com.alishoghi97.notesapp.utils

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.LocalTime

class Converters {
    @TypeConverter
    fun fromTimestamp (value: String?) : LocalDateTime?{
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date : LocalDateTime?):String?{
        return date?.toString()
    }
}