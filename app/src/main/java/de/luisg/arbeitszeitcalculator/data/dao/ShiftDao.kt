package de.luisg.arbeitszeitcalculator.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import de.luisg.arbeitszeitcalculator.data.Shift
import kotlinx.coroutines.flow.Flow
import java.time.Month

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shift WHERE strftime('%m',startDateTime) = :month AND strftime('%Y',startDateTime) = :year")
    fun getByYearMonth(year: Int, month: Month): Flow<List<Shift>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShift(shift: Shift)
}