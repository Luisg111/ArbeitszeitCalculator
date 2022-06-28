package de.luisg.arbeitszeitcalculator.viewmodel.Repository

import androidx.lifecycle.LiveData
import de.luisg.arbeitszeitcalculator.data.Shift
import kotlinx.coroutines.flow.Flow

interface ShiftRepository {
    fun getShiftsForYearMonth(year: Int, month: Int): Flow<List<Shift>>
    fun getShift(id: Int): LiveData<Shift>
    fun addShift(shift: Shift)
    fun removeShift(shift: Shift)
}