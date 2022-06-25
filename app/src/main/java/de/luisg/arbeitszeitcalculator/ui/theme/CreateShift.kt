package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.ui.theme.DateTimePicker.DateTimePicker
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        var start by remember {
            mutableStateOf<LocalDateTime>(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
            )
        }
        var end by remember {
            mutableStateOf<LocalDateTime>(
                LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MINUTES)
            )
        }

        val startDateDialogState = rememberMaterialDialogState()
        val endDateDialogState = rememberMaterialDialogState()

        DateTimePicker(
            headerText = "Beginn auswählen",
            startDefault = start,
            onOkButtonClick = {
                start = it
                end = it
                endDateDialogState.show()
            },
            dateDialogState = startDateDialogState
        )

        DateTimePicker(
            headerText = "Ende auswählen",
            startDefault = end,
            onOkButtonClick = { end = it },
            dateDialogState = endDateDialogState
        )
        Text(
            text = "$start",
            modifier = Modifier
                .clickable { startDateDialogState.show() }
                .padding(6.dp)
        )
        Text(
            text = "$end",
            modifier = Modifier
                .clickable { endDateDialogState.show() }
                .padding(6.dp)
        )

        Button(
            onClick = { storeShift(Shift(start, end)) },
            modifier = Modifier.padding(6.dp)
        ) {
            Text("Schicht eintragen")
        }
    }
}