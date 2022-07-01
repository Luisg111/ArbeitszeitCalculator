package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.util.ShiftOrder

class GetShift(private val repository: ShiftRepository) {
    suspend operator fun invoke(order: ShiftOrder): List<Shift> {
        when (order) {
            ShiftOrder.ascending -> return repository.getAllShifts()
                .sortedBy { it.startDateTime }
            ShiftOrder.descending -> return repository.getAllShifts()
                .sortedByDescending { it.startDateTime }
        }
    }

    suspend operator fun invoke(order: ShiftOrder, year: Int, month: Int): List<Shift> {
        when (order) {
            ShiftOrder.ascending -> return repository.getShiftsForYearMonth(year, month)
                .sortedBy { it.startDateTime }
            ShiftOrder.descending -> return repository.getShiftsForYearMonth(year, month)
                .sortedByDescending { it.startDateTime }
        }
    }

    @Throws(IllegalArgumentException::class)
    suspend operator fun invoke(id: Int): Shift {
        if (id < 1) throw IllegalArgumentException("id must be greater than 0!")
        return repository.getShift(id)
    }
}