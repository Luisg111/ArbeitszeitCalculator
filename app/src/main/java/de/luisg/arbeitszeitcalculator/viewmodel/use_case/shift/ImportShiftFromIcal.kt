package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import biweekly.Biweekly
import biweekly.ICalendar
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import kotlinx.coroutines.flow.lastOrNull
import java.io.BufferedInputStream
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneId

class ImportShiftFromIcal(val repository: ShiftRepository) {
    suspend operator fun invoke(url: String) {
        var cal: ICalendar
        BufferedInputStream(URL(url).openStream()).use {
            cal = Biweekly.parse(it).first()
        }
        repository.getAllShiftsAsFlow().lastOrNull()?.let {
            println("new data")
            for (event in cal.events) {
                val shift = Shift(
                    LocalDateTime.ofInstant(
                        event.dateStart.value.toInstant(),
                        ZoneId.of("Europe/Berlin")
                    ),
                    LocalDateTime.ofInstant(
                        event.dateEnd.value.toInstant(),
                        ZoneId.of("Europe/Berlin")
                    )
                )
                if (!it.contains(shift)) {
                    repository.addShift(shift)
                }
            }
        }
    }
}