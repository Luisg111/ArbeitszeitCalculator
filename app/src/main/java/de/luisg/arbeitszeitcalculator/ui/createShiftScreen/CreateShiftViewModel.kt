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

class CreateShiftViewModel(id: Int?) : ViewModel(), KoinComponent {
    private val shiftUseCases by inject<ShiftUseCases>()

    private val _state = MutableStateFlow(CreateShiftState())
    val state = _state.onStart {
        if (_state.value.loadedId != null) {
            loadShift(_state.value.loadedId!!)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = CreateShiftState(),
    )

    init {
        if (id != null) {
            _state.update { it.copy(loadedId = id) }
        }
    }

    fun addEvent(event: CreateShiftEvent) {
        when (event) {
            is CreateShiftEvent.StartDateChanged -> startDateChanged(event.date)
            is CreateShiftEvent.EndDateChanged -> endDateChanged(event.date)
            is CreateShiftEvent.CreateShift -> storeShift()
        }
    }

    private fun loadShift(id: Int) {
        viewModelScope.launch {
            val shift = shiftUseCases.getShift(id)
            _state.update {
                it.copy(
                    startDate = shift.startDateTime,
                    endDate = shift.endDateTime,
                    endDateModified = true,
                    loadedId = id,
                )
            }

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
    private fun storeShift() {
        viewModelScope.launch {
            try {
                var newShift = Shift(
                    id = _state.value.loadedId,
                    startDateTime = _state.value.startDate,
                    endDateTime = _state.value.endDate,
                )
                var storedShiftId = shiftUseCases.storeShift(newShift)
                _state.update { it.copy(closeWindow = true, loadedId = storedShiftId.toInt()) }/*Toast.makeText(
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