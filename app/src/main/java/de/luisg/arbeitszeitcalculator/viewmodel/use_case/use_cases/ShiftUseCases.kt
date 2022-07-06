package de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases

import android.content.Context
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift.*

data class ShiftUseCases(
    private val repository: ShiftRepository,
    private val context: Context,
) {
    val storeShift = StoreShift(repository)
    val deleteShift = DeleteShift(repository)
    val getShiftLive = GetShiftLive(repository)
    val getShift = GetShift(repository)
    val displayShiftDuration = DisplayShiftDuration()
    val importShiftFromIcal = ImportShiftFromIcal(repository)
    val exportShiftToJson = ExportShiftToJson(context)
    val importShiftFromJson = ImportShiftFromJson(context)
}
