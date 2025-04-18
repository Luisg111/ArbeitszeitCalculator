package de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.shift.data.model.Shift
import java.time.Duration
import java.time.temporal.ChronoUnit

class DisplayShiftDuration {
    operator fun invoke(shift: Shift): String {
        return "${shift.duration.toHours()} h ${
            (shift.duration.toMinutes() % 60).toString().padStart(2, '0')
        } min"
    }

    operator fun invoke(shifts: List<Shift>): String {
        var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
        shifts.forEach { shift ->
            sum = sum.plus(shift.duration)
        }
        return "${sum.toHours()} h ${
            (sum.toMinutes() % 60).toString().padStart(2, '0')
        } min"
    }
}