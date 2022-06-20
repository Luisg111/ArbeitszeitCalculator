package de.luisg.arbeitszeitcalculator.model

import java.time.LocalDateTime
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

    override fun toString(): String {
        return "Shift(startDateTime=$startDateTime, endDateTime=$endDateTime)"
    }

}