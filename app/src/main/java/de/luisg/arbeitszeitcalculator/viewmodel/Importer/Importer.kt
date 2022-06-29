package de.luisg.arbeitszeitcalculator.viewmodel.Importer

interface Importer {
    suspend fun import(path: String)
}