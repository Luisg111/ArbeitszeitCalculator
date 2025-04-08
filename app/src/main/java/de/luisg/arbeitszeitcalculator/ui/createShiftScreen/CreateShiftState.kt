package de.luisg.arbeitszeitcalculator.ui.createShiftScreen

import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class CreateShiftState(
    val loadedId: Int? = null,
    val startDate: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
    val endDate: LocalDateTime = LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MINUTES),
    val endDateModified: Boolean = false,
    val closeWindow: Boolean = false,
)
