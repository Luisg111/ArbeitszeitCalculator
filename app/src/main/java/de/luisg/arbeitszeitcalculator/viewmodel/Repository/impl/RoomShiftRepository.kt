package de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl

import android.content.Context
import androidx.room.Room
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.data.database.ShiftDatabase
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomShiftRepository(context: Context) : ShiftRepository {
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

    override fun addShift(shift: Shift) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            db.shiftDao.addShift(shift)
        }
    }

    override fun removeShift(shift: Shift) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            db.shiftDao.removeShift(shift)
        }
    }
}