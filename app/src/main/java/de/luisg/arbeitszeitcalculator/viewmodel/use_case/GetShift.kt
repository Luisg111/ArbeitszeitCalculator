package de.luisg.arbeitszeitcalculator.viewmodel.use_case

import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.util.ShiftOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class GetShift(val repository: ShiftRepository) {
    inner class GetAllShifts {
        operator fun invoke(order: ShiftOrder): Flow<List<Shift>> {
            return repository.getAllShifts().map { shifts ->
                when (order) {
                    ShiftOrder.ascending -> shifts.sortedBy { it.startDateTime }
                    ShiftOrder.descending -> shifts.sortedByDescending { it.startDateTime }
                }
            }
        }
    }

    inner class GetShiftsForDayMonth {
        operator fun invoke(order: ShiftOrder, year: Int, month: Int): Flow<List<Shift>> {
            return repository.getShiftsForYearMonth(year, month).map { shifts ->
                when (order) {
                    ShiftOrder.ascending -> shifts.sortedBy { it.startDateTime }
                    ShiftOrder.descending -> shifts.sortedByDescending { it.startDateTime }
                }
            }
        }
    }
}