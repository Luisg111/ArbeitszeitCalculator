package de.luisg.arbeitszeitcalculator.shift.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.luisg.arbeitszeitcalculator.shift.data.model.Shift
import de.luisg.arbeitszeitcalculator.shift.domain.util.LocalDateTimeConverter

@Database(
    entities = [Shift::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ShiftDatabase : RoomDatabase() {
    abstract val shiftDao: ShiftDao
}