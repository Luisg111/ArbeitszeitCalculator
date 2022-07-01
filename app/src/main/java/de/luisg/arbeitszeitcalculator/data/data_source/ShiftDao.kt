package de.luisg.arbeitszeitcalculator.data.data_source

import androidx.room.*
import de.luisg.arbeitszeitcalculator.data.model.Shift
import kotlinx.coroutines.flow.Flow

@Dao
interface ShiftDao {
    @Query("SELECT * FROM shift WHERE strftime('%m',startDateTime) = :month AND strftime('%Y',startDateTime) = :year ORDER BY startDateTime DESC")
    fun getByYearMonthAsFlow(year: String, month: String): Flow<List<Shift>>

    @Query("SELECT * FROM shift WHERE strftime('%m',startDateTime) = :month AND strftime('%Y',startDateTime) = :year ORDER BY startDateTime DESC")
    suspend fun getByYearMonth(year: String, month: String): List<Shift>

    @Query("SELECT * FROM shift ORDER BY startDateTime DESC")
    fun getAllAsFlow(): Flow<List<Shift>>

    @Query("SELECT * FROM shift ORDER BY startDateTime DESC")
    suspend fun getAll(): List<Shift>

    @Query("SELECT * FROM shift WHERE id = :id")
    fun getShiftAsFlow(id: Int): Flow<Shift>

    @Query("SELECT * FROM shift WHERE id = :id")
    suspend fun getShift(id: Int): Shift

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShift(shift: Shift)

    @Delete
    suspend fun removeShift(shift: Shift)
}