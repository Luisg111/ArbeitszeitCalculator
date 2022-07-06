package de.luisg.arbeitszeitcalculator.viewmodel.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionChecker {
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    fun updateOrRequestWritePermission(context: Context) {
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val atLeastSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        if (!(permissionGranted || atLeastSdk29)) {
            permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    fun updateOrRequestReadPermission(context: Context) {
        val permissionGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        if (!permissionGranted) {
            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}