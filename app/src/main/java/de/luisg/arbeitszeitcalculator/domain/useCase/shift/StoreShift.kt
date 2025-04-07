package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.repository.ShiftRepository

class StoreShift(
    val repository: ShiftRepository
) {
    @Throws(IllegalArgumentException::class)
    suspend operator fun invoke(shift: Shift) {
        if (shift.startDateTime.isBefore(shift.endDateTime)) {
            repository.addShift(shift)
        } else {
            throw IllegalArgumentException("Shift startDateTime must be before endDateTime")
        }
    }
}