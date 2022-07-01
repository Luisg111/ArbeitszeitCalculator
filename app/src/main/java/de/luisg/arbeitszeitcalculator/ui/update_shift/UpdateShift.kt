package de.luisg.arbeitszeitcalculator.ui.update_shift

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
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.ui.common.DateTimePicker.DateTimePicker
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.shift.ShiftUseCases
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun UpdateShift(
    id: Int,
    shiftUseCases: ShiftUseCases,
    navController: NavHostController
) {
    val context = LocalContext.current
    Column {
        //App bar mit Titel
        TopAppBar(
            title = {
                Text(stringResource(R.string.UpdateShiftHeader))
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
            val newShift by shiftUseCases.getShiftLive(id).collectAsState(
                initial = null
            )

            newShift?.let() {
                var dateStart by remember {
                    mutableStateOf(
                        newShift!!.startDateTime
                    )
                }
                var dateEnd by remember {
                    mutableStateOf(
                        newShift!!.endDateTime
                    )
                }

                //Erzeuge DialogStates für DateTimePicker
                val startDateDialogState = rememberMaterialDialogState()
                val endDateDialogState = rememberMaterialDialogState()

                //DateTimePicker für Start der Schicht
                DateTimePicker(
                    headerText = stringResource(R.string.CreateShiftChooseBegin),
                    startDefault = dateStart,
                    onOkButtonClick = {
                        dateStart = it
                    },
                    dateDialogState = startDateDialogState
                )

                //DateTimePicker für Ende der Schicht
                DateTimePicker(
                    headerText = stringResource(R.string.CreateShiftChooseEnd),
                    startDefault = dateEnd,
                    onOkButtonClick = {
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
                        MainScope().launch {
                            try {
                                shiftUseCases.storeShift(newShift!!)
                                Toast.makeText(
                                    context,
                                    textSuccess,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: IllegalArgumentException) {
                                Toast.makeText(
                                    context,
                                    textError,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
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
}