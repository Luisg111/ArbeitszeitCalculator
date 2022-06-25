package de.luisg.arbeitszeitcalculator.ui.create_shift

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.ui.DateTimePicker.DateTimePicker
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

@Composable
fun CreateShiftView(
    storeShift: (Shift) -> Unit,
    onBackButtonPressed: () -> Unit,
) {
    val context = LocalContext.current
    Column {
        TopAppBar(
            title = {
                Text("Schicht erstellen")
            },
            actions = {
                IconButton(onClick = onBackButtonPressed) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        "go Back",
                        tint = Color.White
                    )
                }
            }
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            val newShift = Shift(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MINUTES)
            )

            var dateStart by remember {
                mutableStateOf(
                    newShift.startDateTime
                )
            }
            var dateEnd by remember {
                mutableStateOf(
                    newShift.endDateTime
                )
            }

            val startDateDialogState = rememberMaterialDialogState()
            val endDateDialogState = rememberMaterialDialogState()

            DateTimePicker(
                headerText = "Beginn auswählen",
                startDefault = newShift.startDateTime,
                onOkButtonClick = {
                    dateStart = it
                    endDateDialogState.show()
                },
                dateDialogState = startDateDialogState
            )

            DateTimePicker(
                headerText = "Ende auswählen",
                startDefault = dateEnd,
                onOkButtonClick = { dateEnd = it },
                dateDialogState = endDateDialogState
            )
            Text(text = "Schichtanfang", fontWeight = FontWeight.ExtraBold)
            Text(
                text = dateStart.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                modifier = Modifier
                    .clickable { startDateDialogState.show() }
                    .padding(6.dp)
            )
            Text(text = "Schichtende", fontWeight = FontWeight.ExtraBold)
            Text(
                text = dateEnd.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                modifier = Modifier
                    .clickable { endDateDialogState.show() }
                    .padding(6.dp)
            )

            Button(
                onClick = {
                    if (dateEnd.isAfter(dateStart)) {
                        newShift.endDateTime = dateEnd
                        newShift.startDateTime = dateStart
                        storeShift(newShift)
                        Toast.makeText(
                            context,
                            "Schicht erfolgreich erstellt!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "Schichtanfang muss vor Schichtende liegen!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier.padding(6.dp)
            ) {
                Text("Schicht eintragen")
            }
        }
    }
}