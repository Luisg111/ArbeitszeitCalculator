package de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.shift.data.model.Shift
import de.luisg.arbeitszeitcalculator.shift.domain.repository.ShiftRepository

class StoreShift(
    val repository: ShiftRepository
) {
    @Throws(IllegalArgumentException::class)
    suspend operator fun invoke(shift: Shift): Long {
        if (shift.startDateTime.isBefore(shift.endDateTime)) {
            return repository.upsertShift(shift)
        } else {
            throw IllegalArgumentException("Shift startDateTime must be before endDateTime")
        }
    }
}