package de.luisg.arbeitszeitcalculator.viewmodel.Repository

import androidx.lifecycle.LiveData
import de.luisg.arbeitszeitcalculator.data.model.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getShiftsForYearMonth(year: Int, month: Int): Flow<List<Shift>>
    fun getAllShifts(): Flow<List<Shift>>
    fun getShift(id: Int): LiveData<Shift>
    suspend fun addShift(shift: Shift)
    suspend fun removeShift(shift: Shift)
}