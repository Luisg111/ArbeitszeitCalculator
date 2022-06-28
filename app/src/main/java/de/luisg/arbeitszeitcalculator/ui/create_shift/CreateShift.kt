package de.luisg.arbeitszeitcalculator.ui.create_shift

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.ui.DateTimePicker.DateTimePicker
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

@Composable
fun CreateShiftView(
    repo: ShiftRepository,
    navController: NavController
) {
    val context = LocalContext.current
    Column {
        //App bar mit Titel
        TopAppBar(
            title = {
                Text(stringResource(R.string.CreateShiftHeadline))
            },
            actions = {
                //Gehe Zurück zur Listenansicht, wenn zurückbutton geklickt
                IconButton(onClick = { navController.navigate("list") }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        "go Back",
                        tint = Color.White
                    )
                }
            }
        )

        //Zeige Start- und Enddatum mit Uhrzeiten
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            //Erzeuge neue Schicht mit default-Daten
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

            //Erzeuge DialogStates für DateTimePicker
            val startDateDialogState = rememberMaterialDialogState()
            val endDateDialogState = rememberMaterialDialogState()

            var endDateModified = remember { false }

            //DateTimePicker für Start der Schicht
            DateTimePicker(
                headerText = stringResource(R.string.CreateShiftChooseBegin),
                startDefault = dateStart,
                onOkButtonClick = {
                    dateStart = it
                    if (!endDateModified) {
                        dateEnd = dateStart.plusHours(4).truncatedTo(ChronoUnit.MINUTES)
                        endDateDialogState.show()
                    }
                },
                dateDialogState = startDateDialogState
            )

            //DateTimePicker für Ende der Schicht
            DateTimePicker(
                headerText = stringResource(R.string.CreateShiftChooseEnd),
                startDefault = dateEnd,
                onOkButtonClick = {
                    endDateModified = true
                    dateEnd = it
                },
                dateDialogState = endDateDialogState
            )

            Text(
                text = stringResource(R.string.CreateShiftBeginHeadline),
                fontWeight = FontWeight.ExtraBold
            )

            //Zeige Schichtanfang
            Text(
                text = dateStart.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable { startDateDialogState.show() }
                    .padding(6.dp)
            )

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(R.string.CreateShiftEndHeadline),
                fontWeight = FontWeight.ExtraBold
            )

            //Zeige Schichtende
            Text(
                text = dateEnd.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable { endDateDialogState.show() }
                    .padding(6.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val textSuccess = stringResource(R.string.CreateShiftSuccessToast)
            val textError = stringResource(R.string.CreateShiftFailureToast)

            //Button zum Eintragen der neuen Schicht
            Button(
                onClick = {
                    if (dateEnd.isAfter(dateStart)) {
                        newShift.endDateTime = dateEnd
                        newShift.startDateTime = dateStart
                        repo.addShift(newShift)
                        endDateModified = false
                        Toast.makeText(
                            context,
                            textSuccess,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            textError,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.CreateShiftButtonText))
            }
        }
    }
}