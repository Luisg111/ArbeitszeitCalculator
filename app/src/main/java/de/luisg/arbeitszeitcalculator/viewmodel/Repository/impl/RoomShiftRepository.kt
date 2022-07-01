package de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl

import android.content.Context
import androidx.lifecycle.LiveData
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

    override fun getShiftsForYearMonth(year: Int, month: Int): Flow<List<Shift>> {
        return db.shiftDao.getByYearMonth("%04d".format(year), "%02d".format(month))
    }

    override fun getAllShifts(): Flow<List<Shift>> {
        return db.shiftDao.getAll()
    }

    override fun getShift(id: Int): LiveData<Shift> {
        return db.shiftDao.getShift(id)
    }

    override suspend fun addShift(shift: Shift) {
        db.shiftDao.addShift(shift)
    }

    override suspend fun removeShift(shift: Shift) {
        db.shiftDao.removeShift(shift)
    }
}