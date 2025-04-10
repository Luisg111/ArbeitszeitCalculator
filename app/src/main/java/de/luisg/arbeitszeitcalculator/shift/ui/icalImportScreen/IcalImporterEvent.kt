package de.luisg.arbeitszeitcalculator.shift.ui.icalImportScreen

sealed interface IcalImporterEvent {
    class UrlChanged(val newUrl: String) : IcalImporterEvent
    class ImportStarted() : IcalImporterEvent
}

