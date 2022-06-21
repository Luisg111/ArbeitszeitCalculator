package de.luisg.arbeitszeitcalculator.model

import java.time.Duration
import java.time.LocalDateTime

class Shift(var startDateTime: LocalDateTime, var endDateTime: LocalDateTime) {
    /**
     * gets the duration of the [Shift]
     *
     * @return the duration of the [Shift]
     */
    fun getShiftDuration(): Duration {
        return Duration.between(startDateTime, endDateTime)
    }

    override fun toString(): String {
        return "Shift(startDateTime=$startDateTime, endDateTime=$endDateTime)"
    }

}