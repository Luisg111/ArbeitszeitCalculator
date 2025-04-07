package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import android.content.Context
import android.net.Uri
import com.fasterxml.jackson.databind.ObjectMapper
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.util.PermissionChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class ExportShiftToJson(private val context: Context) {
    operator fun invoke(shifts: List<Shift>, file: Uri) = MainScope().launch(Dispatchers.IO) {
        val mapper = ObjectMapper().findAndRegisterModules()

        PermissionChecker.updateOrRequestWritePermission(context)

        try {
            context.contentResolver.openOutputStream(file, "rwt").use { stream ->
                mapper.writeValue(stream, shifts)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}