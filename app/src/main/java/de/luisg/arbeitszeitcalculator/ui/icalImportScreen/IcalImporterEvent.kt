package de.luisg.arbeitszeitcalculator.ui.icalImportScreen

sealed interface IcalImporterEvent {
    class UrlChanged(val newUrl: String) : IcalImporterEvent
    class ImportStarted() : IcalImporterEvent
}

