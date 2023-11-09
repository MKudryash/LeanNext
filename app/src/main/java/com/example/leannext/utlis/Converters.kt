package com.example.leannext.utlis

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {
    val format = SimpleDateFormat("yyyy-MM-dd", Locale("ru"))

    @TypeConverter
    fun fromTimestamp(value: String?): Date? {
        return value?.let { format.parse(value) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): String? {
        return format.format(date)
    }
}