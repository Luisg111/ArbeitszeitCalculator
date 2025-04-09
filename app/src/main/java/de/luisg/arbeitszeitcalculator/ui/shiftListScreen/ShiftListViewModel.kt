package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.domain.util.ShiftOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(ExperimentalCoroutinesApi::class)
class ShiftListViewModel : ViewModel(), KoinComponent {
    private val shiftUseCases by inject<ShiftUseCases>()
    private val loanUseCases by inject<LoanUseCases>()

    private var hasLoadedData = false

    private val _state = MutableStateFlow(ShiftListState())

    private val _monthFlow = MutableStateFlow(0)
    private val _yearFlow = MutableStateFlow(0)

    private val _shiftFlow =
        _monthFlow.combine(_yearFlow) { month, year ->
            println("new flow created!")
            month to year
        }.flatMapLatest { latestState ->
            shiftUseCases.getShiftLive(ShiftOrder.Ascending, latestState.second, latestState.first)
        }

    val state = _state.combine(_shiftFlow) { state, shiftItems ->
        state.copy(
            listItems = shiftItems,
            hourSummaryString = shiftUseCases.displayShiftDuration(shiftItems),
            salarySummaryString = "${
                (loanUseCases.getLoan() * (shiftUseCases.getShiftDuration(shiftItems)
                    .toMinutes() / 60.0))
            } €"
        )
    }.combine(_yearFlow) { state, year ->
        state.copy(year = year)
    }.combine(_monthFlow) { state, month ->
        state.copy(month = month)
    }.onStart {
        if (!hasLoadedData) {
            loadData()
            hasLoadedData = true
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = ShiftListState()
    )

    fun addEvent(event: ShiftListEvent) {
        when (event) {
            is ShiftListEvent.ExportToJson -> exportToJson(event.uri)
            is ShiftListEvent.ImportFromJson -> importFromJson(event.uri)
            is ShiftListEvent.IoMenuToggled -> ioMenuToggled(event.isOpen)
            is ShiftListEvent.DeleteAllShifts -> deleteAllShifts()
            is ShiftListEvent.SelectedYearChanged -> selectedYearChanged(event.year)
            is ShiftListEvent.SelectedMonthChanged -> selectedMonthChanged(event.month)
            is ShiftListEvent.SettingsMenuToggled -> settingsMenuToggled()
            is ShiftListEvent.SalaryChanged -> salaryChanged(event.salary)
            is ShiftListEvent.MonthOverviewToggled -> monthOverviewToggled(event.isOpen)
            is ShiftListEvent.MonthMenuToggled -> monthMenuToggled(event.isOpen)
        }
    }

    private fun loadData() {
        val salary = loanUseCases.getLoan()
        _state.update {
            it.copy(salary = salary.toString())
        }
        _monthFlow.update {
            _state.value.month
        }
        _yearFlow.update {
            _state.value.year
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
        _yearFlow.update {
            year
        }
    }

    private fun selectedMonthChanged(month: Int) {
        _monthFlow.update {
            month
        }
    }

    private fun settingsMenuToggled() {
        _state.update {
            it.copy(settingsExtended = !it.settingsExtended)
        }
    }

    //TODO: add error handling
    private fun salaryChanged(salary: String) {
        val parsedValue = salary.toDoubleOrNull()

        if (parsedValue != null) {
            loanUseCases.setLoan(parsedValue)
        }

        _state.update {
            it.copy(salary = salary, salaryError = parsedValue == null)
        }


    }

    private fun monthOverviewToggled(isOpen: Boolean?) {
        _state.update {
            it.copy(monthOverviewExtended = isOpen ?: !it.monthOverviewExtended)
        }
    }

    private fun monthMenuToggled(isOpen: Boolean?) {
        _state.update {
            it.copy(monthMenuOpen = isOpen ?: !it.monthMenuOpen)
        }
    }
}