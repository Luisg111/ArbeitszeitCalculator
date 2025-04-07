package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.repository.ShiftRepository

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