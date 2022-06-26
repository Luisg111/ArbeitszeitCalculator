package de.luisg.arbeitszeitcalculator.viewmodel.Repository

import de.luisg.arbeitszeitcalculator.data.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getShiftsForYearMonth(year: Int, month: Int): Flow<List<Shift>>
    fun addShift(shift: Shift)
    fun removeShift(shift: Shift)
}