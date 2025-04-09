package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import java.time.LocalDateTime

data class ShiftListState(
    val year: Int = LocalDateTime.now().year,
    val month: Int = LocalDateTime.now().month.value,
    val sallary: Double = 0.0,
    val maxHours: Int = 0,
    val ioMenuExtended: Boolean = false,
    val settingsExtended: Boolean = false,
)
