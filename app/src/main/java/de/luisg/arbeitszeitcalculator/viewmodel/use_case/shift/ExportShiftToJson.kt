package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import android.os.Environment
import com.fasterxml.jackson.databind.ObjectMapper
import de.luisg.arbeitszeitcalculator.data.model.Shift
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class ExportShiftToJson {
    operator fun invoke(shifts: List<Shift>) = MainScope().launch(Dispatchers.IO) {
        val externalStorage =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val mapper = ObjectMapper().findAndRegisterModules()

        mapper.writeValue(File(externalStorage, "ShiftOutput.json"), shifts)
    }
}