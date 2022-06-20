package de.luisg.arbeitszeitcalculator.viewmodel

import biweekly.Biweekly
import biweekly.ICalendar
import de.luisg.arbeitszeitcalculator.model.Shift
import java.io.BufferedInputStream
import java.net.URL
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class ShiftHandler(private var icalAdress: String) {
    private val events = mutableListOf<Shift>()

    fun updateData() {
        var cal: ICalendar
        BufferedInputStream(URL(icalAdress).openStream()).use {
            cal = Biweekly.parse(it).first();
        }

        cal.events.forEach() { event ->
            println(Shift(
                LocalDateTime.from(event.dateStart.value.toInstant()),
                LocalDateTime.from(event.dateEnd.value.toInstant())
            ))
        }
    }


    /**
     * returns [Shift] for the given [Year]/[Month].
     *
     * @param month the [Month] for which [Shift] should be returned
     * @param year the [Year] for which [Shift] should be returned
     *
     * @return a [List] of Shits. The [List] can be empty
     */
    fun getShiftsForMonth(month: Month, year: Year) {

    }
}