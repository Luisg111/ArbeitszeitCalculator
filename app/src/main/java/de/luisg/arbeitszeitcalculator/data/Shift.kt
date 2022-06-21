package de.luisg.arbeitszeitcalculator.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration
import java.time.LocalDateTime

@Entity(tableName = "shift")
data class Shift(
    val startDateTime: LocalDateTime,
    val endDateTime: LocalDateTime,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {


    /**
     * gets the duration of the [Shift]
     *
     * @return the duration of the [Shift]
     */
    fun getShiftDuration(): Duration {
        return Duration.between(startDateTime, endDateTime)
    }
}