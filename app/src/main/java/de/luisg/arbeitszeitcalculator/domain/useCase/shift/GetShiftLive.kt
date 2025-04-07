package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.domain.util.ShiftOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetShiftLive(private val repository: ShiftRepository) {
    operator fun invoke(order: ShiftOrder): Flow<List<Shift>> {
        return repository.getAllShiftsAsFlow().map { shifts ->
            when (order) {
                ShiftOrder.Ascending -> shifts.sortedBy { it.startDateTime }
                ShiftOrder.Descending -> shifts.sortedByDescending { it.startDateTime }
            }
        }
    }

    operator fun invoke(order: ShiftOrder, year: Int, month: Int): Flow<List<Shift>> {
        return repository.getShiftsForYearMonthAsFlow(year, month).map { shifts ->
            when (order) {
                ShiftOrder.Ascending -> shifts.sortedBy { it.startDateTime }
                ShiftOrder.Descending -> shifts.sortedByDescending { it.startDateTime }
            }
        }
    }

    @Throws(IllegalArgumentException::class)
    operator fun invoke(id: Int): Flow<Shift> {
        if (id < 1) throw IllegalArgumentException("id must be greater than 0!")
        return repository.getShiftAsFlow(id)
    }
}