package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

class ImportShiftFromJson(val repository: ShiftRepository) {
    suspend operator fun invoke(file: File) {
        val mapper = ObjectMapper().findAndRegisterModules()

        BufferedReader(FileReader(file)).use { stream ->
            mapper.readValue(stream, object : TypeReference<List<Shift>>() {}).forEach { shift ->
                repository.addShift(shift)
            }
        }
    }
}