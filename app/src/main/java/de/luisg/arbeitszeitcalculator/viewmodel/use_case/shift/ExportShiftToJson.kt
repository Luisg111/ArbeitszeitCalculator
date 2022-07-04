package de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.core.net.toUri
import com.fasterxml.jackson.databind.ObjectMapper
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.util.PermissionChecker
import de.luisg.arbeitszeitcalculator.viewmodel.util.sdk29AndUp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class ExportShiftToJson(private val context: Context) {
    operator fun invoke(shifts: List<Shift>) = MainScope().launch(Dispatchers.IO) {
        val mapper = ObjectMapper().findAndRegisterModules()

        PermissionChecker.updateOrRequestWritePermission(context)

        val storageLocation = sdk29AndUp {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } ?: Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toUri()

        try {
            val contentValues = ContentValues().apply {
                put(MediaStore.Files.FileColumns.DISPLAY_NAME, "ArbeitszeitCalculator.json")
            }
            context.contentResolver.insert(storageLocation, contentValues)?.also { uri ->
                context.contentResolver.openOutputStream(uri).use { stream ->
                    mapper.writeValue(stream, shifts)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}