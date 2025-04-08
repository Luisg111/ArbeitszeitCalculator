package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import android.net.Uri

sealed interface ShiftListEvent {
    data class ImportFromJson(val uri: Uri) : ShiftListEvent
    data class ExportToJson(val uri: Uri) : ShiftListEvent
    class IoMenuToggled(val isOpen: Boolean? = null) : ShiftListEvent
    class DeleteAllShifts() : ShiftListEvent
    data class SelectedYearChanged(val year: Int) : ShiftListEvent
    data class SelectedMonthChanged(val month: Int) : ShiftListEvent

}