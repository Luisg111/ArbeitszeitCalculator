package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import android.net.Uri

sealed interface ShiftListEvent {
    data class ImportFromJson(val uri: Uri) : ShiftListEvent
    data class ExportToJson(val uri: Uri) : ShiftListEvent
    data class IoMenuToggled(val isOpen: Boolean? = null) : ShiftListEvent
    class DeleteAllShifts() : ShiftListEvent
    data class SelectedYearChanged(val year: Int) : ShiftListEvent
    data class SelectedMonthChanged(val month: Int) : ShiftListEvent
    data class SettingsMenuToggled(val isOpen: Boolean? = null) : ShiftListEvent
    data class SalaryChanged(val salary: String) : ShiftListEvent
}