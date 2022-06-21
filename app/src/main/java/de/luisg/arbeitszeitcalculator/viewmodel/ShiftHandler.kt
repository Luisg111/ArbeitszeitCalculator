package de.luisg.arbeitszeitcalculator.viewmodel

import androidx.compose.runtime.mutableStateListOf
import biweekly.Biweekly
import biweekly.ICalendar
import de.luisg.arbeitszeitcalculator.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import java.net.URL
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class ShiftHandler(private var icalAdress: String) {
    private val events = mutableStateListOf<Shift>()

    init {
        updateData()
    }

    fun updateData() {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            var cal: ICalendar
            BufferedInputStream(URL(icalAdress).openStream()).use {
                cal = Biweekly.parse(it).first()
            }

            cal.events.forEach() { event ->
                events.add(
                    Shift(
                        LocalDateTime.ofInstant(
                            event.dateStart.value.toInstant(),
                            ZoneId.of("Europe/Berlin")
                        ),
                        LocalDateTime.ofInstant(
                            event.dateEnd.value.toInstant(),
                            ZoneId.of("Europe/Berlin")
                        )
                    )
                )
            }
        }
    }

    fun getShiftsByYearMonth(year: Int, month: Month): List<Shift> {
        return events.filter { shift ->
            shift.startDateTime.year == year && shift.startDateTime.month == month
        }.sortedBy { shift ->
            shift.startDateTime
        }
    }

    fun getTotalDurationByYearMonth(year: Int, month: Month): Duration {
        var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
        events.filter { shift ->
            shift.startDateTime.year == year && shift.startDateTime.month == month
        }.forEach { shift ->
            sum = sum.plus(shift.getShiftDuration())
        }
        return sum
    }
}