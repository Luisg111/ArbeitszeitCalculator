package de.luisg.arbeitszeitcalculator.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import de.luisg.arbeitszeitcalculator.data.Shift
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shift WHERE strftime('%m',startDateTime) = :month AND strftime('%Y',startDateTime) = :year")
    fun getByYearMonth(year: String, month: String): Flow<List<Shift>>

    @Query("SELECT * FROM shift")
    fun getAll(): Flow<List<Shift>>

    @Query("SELECT * FROM shift WHERE id = :id")
    fun getShift(id: Int): LiveData<Shift>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShift(shift: Shift)

    @Delete
    suspend fun removeShift(shift: Shift)
}