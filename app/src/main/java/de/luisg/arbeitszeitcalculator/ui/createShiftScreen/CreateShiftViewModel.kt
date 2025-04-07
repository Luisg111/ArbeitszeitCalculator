package de.luisg.arbeitszeitcalculator.ui.createShiftScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CreateShiftViewModel : ViewModel(), KoinComponent {
    private val shiftUseCases by inject<ShiftUseCases>()

    private val _state = MutableStateFlow(CreateShiftState())
    val state = _state.onStart {
        //initData
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = CreateShiftState()
    )

    fun addEvent(event: CreateShiftEvent) {
        when (event) {
            is CreateShiftEvent.StartDateChanged -> startDateChanged(event.date)
            is CreateShiftEvent.EndDateChanged -> endDateChanged(event.date)
            is CreateShiftEvent.CreateShift -> createShift()
        }
    }

    private fun startDateChanged(date: LocalDateTime) {
        _state.update {
            it.copy(
                startDate = date.truncatedTo(ChronoUnit.MINUTES),
                endDate = if (it.endDateModified) it.endDate else it.endDate.plusHours(4)
            )
        }
    }

    private fun endDateChanged(date: LocalDateTime) {
        _state.update {
            it.copy(
                endDate = date.truncatedTo(ChronoUnit.MINUTES), endDateModified = true
            )
        }
    }

    //TODO:Add Error Handling
    private fun createShift() {
        viewModelScope.launch {
            try {
                var newShift = Shift(
                    startDateTime = _state.value.startDate,
                    endDateTime = _state.value.endDate,
                )
                shiftUseCases.storeShift(newShift)
                _state.update { it.copy(closeWindow = true) }/*Toast.makeText(
                    context,
                    textSuccess,
                    Toast.LENGTH_SHORT
                ).show()*/
            } catch (e: IllegalArgumentException) {/*Toast.makeText(
                    context,
                    textError,
                    Toast.LENGTH_SHORT
                ).show()*/
            }
        }
    }
}