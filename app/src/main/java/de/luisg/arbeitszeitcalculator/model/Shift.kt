package de.luisg.arbeitszeitcalculator.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

class Shift(var startDateTime: LocalDateTime, var endDateTime: LocalDateTime) {
    /**
     * gets the duration of the [Shift]
     * @param unit the [ChronoUnit] in which the result should be returned
     *
     * @return the duration of the [Shift] in the given [ChronoUnit]
     */
    fun getShiftDuration(unit: ChronoUnit): Long {
        return unit.between(startDateTime, endDateTime)
    }
}