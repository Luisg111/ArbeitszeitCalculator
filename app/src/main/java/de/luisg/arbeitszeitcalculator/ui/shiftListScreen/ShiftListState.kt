package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import java.time.LocalDateTime

data class ShiftListState(
    val year: Int = LocalDateTime.now().year,
    val month: Int = LocalDateTime.now().month.value,
    val salary: String = "",
    val salaryError: Boolean = false,
    val ioMenuExtended: Boolean = false,
    val settingsExtended: Boolean = false,
    val monthMenuOpen: Boolean = false,
    val monthOverviewExtended: Boolean = false,
    val hourSummaryString: String = "",
    val salarySummaryString: String = "",
)
