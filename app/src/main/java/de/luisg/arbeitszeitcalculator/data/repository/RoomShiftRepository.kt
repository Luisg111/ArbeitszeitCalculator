package de.luisg.arbeitszeitcalculator.data.repository

import android.content.Context
import androidx.room.Room
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.data.database.ShiftDatabase
import de.luisg.arbeitszeitcalculator.viewmodel.ShiftRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Month

class RoomShiftRepository(context: Context) : ShiftRepository {
    val db: ShiftDatabase

    init {
        db = Room.databaseBuilder(
            context,
            ShiftDatabase::class.java, "database-name"
        ).build()
    }

    override fun getShiftsForYearMonth(year: Int, month: Month): Flow<List<Shift>> {
        return db.shiftDao.getByYearMonth(year, month)
    }

    override fun addShift(shift: Shift) {
        CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
            db.shiftDao.addShift(shift)
        }
    }
}