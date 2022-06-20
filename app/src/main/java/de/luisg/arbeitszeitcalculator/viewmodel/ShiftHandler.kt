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
import java.time.LocalDateTime
import java.time.Month
import java.time.Year
import java.time.ZoneId

class ShiftHandler(private var icalAdress: String) {
    private val events = mutableStateListOf<Shift>()

    init {
        updateData()
    }

    fun updateData() {
        CoroutineScope(Dispatchers.IO).launch {
            var cal: ICalendar
            BufferedInputStream(URL(icalAdress).openStream()).use {
                cal = Biweekly.parse(it).first();
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

    fun getShiftsByYearMonth(year: Year, month: Month): List<Shift> {
        return events.filter { shift ->
            shift.startDateTime.year == year.value && shift.startDateTime.month == month
        }
    }
}