package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import de.luisg.arbeitszeitcalculator.data.Shift
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.FormatStyle

@Composable
fun createShiftView(
    storeShift: (Shift) -> Unit
) {
    var startTime = remember {
        LocalDateTime.now()
    }
    var endTime = remember {
        LocalDateTime.now().plusHours(4)
    }

    Column() {
        dateTimePicker(startTime, onEntry = { startTime = it })
        dateTimePicker(endTime, onEntry = {
            endTime = it
            storeShift(Shift(startTime, endTime))
        })
    }
}

@Composable
private fun dateTimePicker(
    default: LocalDateTime,
    onEntry: (date: LocalDateTime) -> Unit
) {
    var dateText by remember {
        mutableStateOf(
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                .format(default)
        )
    }
    TextField(value = dateText,
        onValueChange = { dateText = it },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onAny = {
                try {
                    onEntry(
                        LocalDateTime.parse(
                            dateText,
                            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
                        )
                    )
                } catch (e: DateTimeParseException) {

                }
            }
        )
    )
}