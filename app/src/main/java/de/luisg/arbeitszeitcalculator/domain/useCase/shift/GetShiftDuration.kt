package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import java.time.Duration
import java.time.temporal.ChronoUnit

class GetShiftDuration {
    operator fun invoke(shifts: List<Shift>): Duration {
        var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
        shifts.forEach { shift ->
            sum = sum.plus(shift.duration)
        }
        return sum
    }
}