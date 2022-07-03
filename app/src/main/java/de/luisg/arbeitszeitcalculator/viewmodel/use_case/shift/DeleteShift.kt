package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository

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