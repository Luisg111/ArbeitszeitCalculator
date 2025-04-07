package de.luisg.arbeitszeitcalculator.ui.icalImportScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IcalImporterViewModel() : ViewModel(), KoinComponent {
    private val shiftUseCases by inject<ShiftUseCases>()

    private val _state = MutableStateFlow(IcalImporterState())
    val state = _state.onStart {
        //initData
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = IcalImporterState()
    )

    fun addEvent(event: IcalImporterEvent) {
        when (event) {
            is IcalImporterEvent.UrlChanged -> urlChanged(event.newUrl)
            is IcalImporterEvent.ImportStarted -> importIcal()
        }
    }

    private fun urlChanged(newUrl: String) {
        _state.update { it.copy(url = newUrl) }
    }

    private fun importIcal() {
        viewModelScope.launch {
            shiftUseCases.importShiftFromIcal(state.value.url) { error ->
                println(error)
            }
            _state.update {
                it.copy(closeWindow = true)
            }
        }
    }
}