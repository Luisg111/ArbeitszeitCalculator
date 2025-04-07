package de.luisg.arbeitszeitcalculator.ui.createShiftScreen

import java.time.LocalDateTime

sealed interface CreateShiftEvent {
    class StartDateChanged(val date: LocalDateTime) : CreateShiftEvent
    class EndDateChanged(val date: LocalDateTime) : CreateShiftEvent
    class CreateShift() : CreateShiftEvent
}