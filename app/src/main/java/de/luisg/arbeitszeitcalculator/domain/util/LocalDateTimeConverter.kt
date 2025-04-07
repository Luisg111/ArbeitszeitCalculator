package de.luisg.arbeitszeitcalculator.domain.util

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeConverter {
    @TypeConverter
    fun toDate(time: String): LocalDateTime {
        return LocalDateTime.parse(time, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }

    @TypeConverter
    fun toTimeString(time: LocalDateTime): String {
        return time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}