package de.luisg.arbeitszeitcalculator.viewmodel.Importer.impl

import biweekly.Biweekly
import biweekly.ICalendar
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Importer.Importer
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import java.io.BufferedInputStream
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneId

class IcalImporter(
    val repository: ShiftRepository,
) : Importer {
    override suspend fun import(path: String) {
        var cal: ICalendar
        BufferedInputStream(URL(path).openStream()).use {
            cal = Biweekly.parse(it).first()
        }
        repository.getAllShifts().let {
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