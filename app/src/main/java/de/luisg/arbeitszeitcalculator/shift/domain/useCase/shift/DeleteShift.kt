package de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.shift.data.model.Shift
import de.luisg.arbeitszeitcalculator.shift.domain.repository.ShiftRepository

class DeleteShift(
    private val repository: ShiftRepository
) {
    suspend operator fun invoke(shift: Shift) {
        repository.removeShift(shift)
    }

    suspend operator fun invoke() {
        repository.removeAllShift()
    }
}