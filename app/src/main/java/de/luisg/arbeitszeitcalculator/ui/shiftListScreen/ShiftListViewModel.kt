package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.domain.util.ShiftOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ShiftListViewModel : ViewModel(), KoinComponent {
    private val shiftUseCases by inject<ShiftUseCases>()
    private val loanUseCases by inject<LoanUseCases>()


    private val _state = MutableStateFlow(ShiftListState())
    val state = _state.onStart {
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ShiftListState()
    )

    private var _listItems: StateFlow<List<Shift>> =
        shiftUseCases.getShiftLive(ShiftOrder.Ascending, _state.value.year, _state.value.month)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )
    val listItems
        get() = _listItems

    fun addEvent(event: ShiftListEvent) {
        when (event) {
            is ShiftListEvent.ExportToJson -> exportToJson(event.uri)
            is ShiftListEvent.ImportFromJson -> importFromJson(event.uri)
            is ShiftListEvent.IoMenuToggled -> ioMenuToggled(event.isOpen)
            is ShiftListEvent.DeleteAllShifts -> deleteAllShifts()
            is ShiftListEvent.SelectedYearChanged -> selectedYearChanged(event.year)
            is ShiftListEvent.SelectedMonthChanged -> selectedMonthChanged(event.month)
        }
    }

    private fun exportToJson(uri: Uri) {
        viewModelScope.launch {
            shiftUseCases.exportShiftToJson(
                shiftUseCases.getShift(ShiftOrder.Ascending), uri
            )
        }
    }

    private fun importFromJson(uri: Uri) {
        viewModelScope.launch {
            shiftUseCases.importShiftFromJson(uri)?.forEach {
                shiftUseCases.storeShift(it)
            }
        }
    }

    private fun ioMenuToggled(isOpen: Boolean?) {
        _state.update {
            it.copy(ioMenuExtended = isOpen ?: !it.ioMenuExtended)
        }
    }

    private fun deleteAllShifts() {
        viewModelScope.launch {
            shiftUseCases.deleteShift()
        }
    }

    private fun selectedYearChanged(year: Int) {
        _state.update {
            it.copy(year = year)
        }
        resetListItemFlow()
    }

    private fun selectedMonthChanged(month: Int) {
        _state.update {
            it.copy(month = month)
        }
        resetListItemFlow()
    }

    private fun resetListItemFlow() {
        _listItems =
            shiftUseCases.getShiftLive(ShiftOrder.Ascending, _state.value.year, _state.value.month)
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000L),
                    initialValue = listOf()
                )
    }
}