package de.luisg.arbeitszeitcalculator.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import de.luisg.arbeitszeitcalculator.domain.util.DateSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.time.Duration
import java.time.LocalDateTime

@Entity(tableName = "shift")
@Serializable
data class Shift(
    @Serializable(with = DateSerializer::class) var startDateTime: LocalDateTime,
    @Serializable(with = DateSerializer::class) var endDateTime: LocalDateTime,
    @PrimaryKey(autoGenerate = true) @Transient val id: Int? = null
) {

    val duration: Duration
        get() {
            return Duration.between(startDateTime, endDateTime)
        }
}