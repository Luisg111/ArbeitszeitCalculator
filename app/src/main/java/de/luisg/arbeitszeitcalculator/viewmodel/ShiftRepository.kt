package de.luisg.arbeitszeitcalculator.viewmodel

import de.luisg.arbeitszeitcalculator.data.Shift
import kotlinx.coroutines.flow.Flow
import java.time.Month

interface ShiftRepository {
    fun getShiftsForYearMonth(year: Int, month: Month): Flow<List<Shift>>
    fun addShift(shift: Shift): Unit
}