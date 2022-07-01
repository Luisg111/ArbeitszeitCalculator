package de.luisg.arbeitszeitcalculator.viewmodel.use_case

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository

class DeleteShift(
    val repository: ShiftRepository
) {
    suspend operator fun invoke(shift: Shift) {
        repository.removeShift(shift)
    }
}