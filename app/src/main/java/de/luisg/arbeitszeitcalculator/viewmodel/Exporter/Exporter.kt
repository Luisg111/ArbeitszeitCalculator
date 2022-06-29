package de.luisg.arbeitszeitcalculator.viewmodel.Exporter

import java.nio.file.Path

interface Exporter {
    suspend fun export(path: Path)
}