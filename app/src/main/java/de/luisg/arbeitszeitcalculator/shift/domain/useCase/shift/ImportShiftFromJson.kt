package de.luisg.arbeitszeitcalculator.shift.domain.useCase.shift

import android.content.Context
import android.net.Uri
import de.luisg.arbeitszeitcalculator.shift.data.model.Shift
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

class ImportShiftFromJson(val context: Context) {
    @OptIn(ExperimentalSerializationApi::class)
    operator fun invoke(file: Uri): List<Shift>? {
        var shifts: List<Shift>? = listOf()

        context.contentResolver.openInputStream(file).use { stream ->
            shifts = stream?.let { Json.decodeFromStream<List<Shift>>(it) }
        }
        return shifts
    }
}