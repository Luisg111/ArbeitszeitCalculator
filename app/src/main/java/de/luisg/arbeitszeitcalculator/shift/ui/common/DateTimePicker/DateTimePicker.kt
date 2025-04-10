package de.luisg.arbeitszeitcalculator.shift.ui.common.DateTimePicker

import androidx.compose.runtime.Composable
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDateTime

@Composable
fun DateTimePicker(
    headerText: String,
    startDefault: LocalDateTime,
    onOkButtonClick: (start: LocalDateTime) -> Unit,
    dateDialogState: MaterialDialogState,
) {
    var date = startDefault.toLocalDate()
    var time = startDefault.toLocalTime()

    val timeDialogState = rememberMaterialDialogState()

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("weiter") {
                timeDialogState.show()
            }
            negativeButton("Cancel")
        }
    ) {
        datepicker(
            initialDate = date,
            title = headerText
        ) {
            date = it
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton("Ok") {
                onOkButtonClick(LocalDateTime.of(date, time))
            }
            negativeButton("Cancel")
        }
    ) {
        timepicker(is24HourClock = true, title = headerText, initialTime = time) {
            time = it
        }
    }
}