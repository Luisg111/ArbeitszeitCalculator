package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.ui.DateTimePicker.DateTimePicker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun CreateShiftView(
    storeShift: (Shift) -> Unit,
    shift: Shift? = null
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        val newShift = shift ?: Shift(
            LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
            LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MINUTES)
        )

        val startDateDialogState = rememberMaterialDialogState()
        val endDateDialogState = rememberMaterialDialogState()

        DateTimePicker(
            headerText = "Beginn auswählen",
            startDefault = newShift.startDateTime,
            onOkButtonClick = {
                newShift.startDateTime = it
                newShift.endDateTime = it
                endDateDialogState.show()
            },
            dateDialogState = startDateDialogState
        )

        DateTimePicker(
            headerText = "Ende auswählen",
            startDefault = newShift.endDateTime,
            onOkButtonClick = { newShift.endDateTime = it },
            dateDialogState = endDateDialogState
        )
        Text(
            text = "${newShift.startDateTime}",
            modifier = Modifier
                .clickable { startDateDialogState.show() }
                .padding(6.dp)
        )
        Text(
            text = "${newShift.endDateTime}",
            modifier = Modifier
                .clickable { endDateDialogState.show() }
                .padding(6.dp)
        )

        Button(
            onClick = { storeShift(newShift) },
            modifier = Modifier.padding(6.dp)
        ) {
            Text("Schicht eintragen")
        }
    }
}