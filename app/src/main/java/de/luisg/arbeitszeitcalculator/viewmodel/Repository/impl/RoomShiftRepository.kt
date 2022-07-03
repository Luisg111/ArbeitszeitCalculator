package de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.room.Room
import de.luisg.arbeitszeitcalculator.data.data_source.ShiftDatabase
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import kotlinx.coroutines.flow.Flow

class RoomShiftRepository(context: Context) : ShiftRepository, ViewModel() {
    val db: ShiftDatabase

    init {
        db = Room.databaseBuilder(
            context,
            ShiftDatabase::class.java, "database-name"
        ).build()
    }

    override fun getShiftsForYearMonthAsFlow(year: Int, month: Int): Flow<List<Shift>> {
        return db.shiftDao.getByYearMonthAsFlow("%04d".format(year), "%02d".format(month))
    }

    override suspend fun getShiftsForYearMonth(year: Int, month: Int): List<Shift> {
        return db.shiftDao.getByYearMonth("%04d".format(year), "%02d".format(month))
    }

    override fun getAllShiftsAsFlow(): Flow<List<Shift>> {
        return db.shiftDao.getAllAsFlow()
    }

    override suspend fun getAllShifts(): List<Shift> {
        return db.shiftDao.getAll()
    }

    override fun getShiftAsFlow(id: Int): Flow<Shift> {
        return db.shiftDao.getShiftAsFlow(id)
    }

    override suspend fun getShift(id: Int): Shift {
        return db.shiftDao.getShift(id)
    }

    override suspend fun addShift(shift: Shift) {
        db.shiftDao.addShift(shift)
    }

    override suspend fun removeShift(shift: Shift) {
        db.shiftDao.removeShift(shift)
    }

    override suspend fun removeAllShift() {
        db.shiftDao.removeAllShift()
    }
}