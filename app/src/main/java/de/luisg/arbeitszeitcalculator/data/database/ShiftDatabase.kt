package de.luisg.arbeitszeitcalculator.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.data.dao.ShiftDao
import de.luisg.arbeitszeitcalculator.viewmodel.typeconverter.LocalDateTimeConverter

@Database(
    entities = [Shift::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class ShiftDatabase : RoomDatabase() {
    abstract val shiftDao: ShiftDao
}