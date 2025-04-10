package de.luisg.arbeitszeitcalculator.shift.domain.useCase.use_cases

import android.content.Context
import de.luisg.arbeitszeitcalculator.shift.domain.repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.DeleteShift
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.DisplayShiftDuration
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.ExportShiftToJson
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.GetShift
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.GetShiftDuration
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.GetShiftLive
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.ImportShiftFromIcal
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.ImportShiftFromJson
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift.StoreShift

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
    val getShiftDuration = GetShiftDuration()
}
