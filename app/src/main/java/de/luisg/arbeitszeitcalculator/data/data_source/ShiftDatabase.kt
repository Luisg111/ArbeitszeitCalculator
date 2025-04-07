package de.luisg.arbeitszeitcalculator.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.util.LocalDateTimeConverter

@Database(
    entities = [Shift::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ShiftDatabase : RoomDatabase() {
    abstract val shiftDao: ShiftDao
}