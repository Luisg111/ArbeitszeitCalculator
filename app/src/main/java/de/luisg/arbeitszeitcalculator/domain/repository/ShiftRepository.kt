package de.luisg.arbeitszeitcalculator.domain.repository

import de.luisg.arbeitszeitcalculator.data.model.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getShiftsForYearMonthAsFlow(year: Int, month: Int): Flow<List<Shift>>
    suspend fun getShiftsForYearMonth(year: Int, month: Int): List<Shift>

    fun getAllShiftsAsFlow(): Flow<List<Shift>>
    suspend fun getAllShifts(): List<Shift>

    fun getShiftAsFlow(id: Int): Flow<Shift>
    suspend fun getShift(id: Int): Shift

    suspend fun addShift(shift: Shift)

    suspend fun removeShift(shift: Shift)

    suspend fun removeAllShift()
}