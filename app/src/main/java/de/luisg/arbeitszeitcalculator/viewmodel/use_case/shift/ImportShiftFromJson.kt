package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import android.content.Context
import android.net.Uri
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import de.luisg.arbeitszeitcalculator.data.model.Shift

class ImportShiftFromJson(val context: Context) {
    suspend operator fun invoke(file: Uri): List<Shift>? {
        val mapper = jacksonObjectMapper().findAndRegisterModules()
        var shifts: List<Shift>? = null

        context.contentResolver.openInputStream(file).use { stream ->
            shifts = stream?.let { mapper.readValue(it) }
        }
        return shifts
    }
}