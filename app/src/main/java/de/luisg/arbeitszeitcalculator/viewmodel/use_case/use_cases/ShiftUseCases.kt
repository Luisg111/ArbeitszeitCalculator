package de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases

import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift.*

data class ShiftUseCases(
    private val repository: ShiftRepository,
) {
    val storeShift = StoreShift(repository)
    val deleteShift = DeleteShift(repository)
    val getShiftLive = GetShiftLive(repository)
    val getShift = GetShift(repository)
    val displayShiftDuration = DisplayShiftDuration()
    val importShiftFromIcal = ImportShiftFromIcal(repository)
    val exportShiftToJson = ExportShiftToJson()
}
