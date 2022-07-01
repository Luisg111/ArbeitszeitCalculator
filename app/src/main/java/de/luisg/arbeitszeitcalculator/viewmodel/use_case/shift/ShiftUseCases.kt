package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

data class ShiftUseCases(
    val storeShift: StoreShift,
    val deleteShift: DeleteShift,
    val getShiftLive: GetShiftLive,
    val getShift: GetShift,
    val displayShiftDuration: DisplayShiftDuration,
    val importShiftFromIcal: ImportShiftFromIcal
)
