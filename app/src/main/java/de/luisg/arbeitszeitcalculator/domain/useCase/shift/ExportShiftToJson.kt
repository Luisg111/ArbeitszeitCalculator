package de.luisg.arbeitszeitcalculator.domain.useCase.shift

import android.content.Context
import android.net.Uri
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.util.PermissionChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToStream
import java.io.IOException

class ExportShiftToJson(private val context: Context) {
    @OptIn(ExperimentalSerializationApi::class)
    operator fun invoke(shifts: List<Shift>, file: Uri) = MainScope().launch(Dispatchers.IO) {
        PermissionChecker.updateOrRequestWritePermission(context)

        try {
            context.contentResolver.openOutputStream(file, "rwt").use { stream ->
                if (stream != null) Json.encodeToStream(shifts, stream)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}