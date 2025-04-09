package de.luisg.arbeitszeitcalculator.ui.createShiftScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.ui.common.DateTimePicker.DateTimePicker
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.ChronoUnit

@Composable
fun CreateShiftRoot(
    onNavigateToList: () -> Unit,
    shiftId: Int?,
) {
    val viewModel: CreateShiftViewModel = koinViewModel {
        parametersOf(shiftId)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.closeWindow == true) {
        onNavigateToList()
    }

    CreateShiftView(viewModel = viewModel, state = state, onNavigateToList = { onNavigateToList() })
}

@Composable
fun CreateShiftView(
    viewModel: CreateShiftViewModel, state: CreateShiftState, onNavigateToList: () -> Unit
) {
    var startDialogState = rememberMaterialDialogState()
    var endDialogState = rememberMaterialDialogState()

    Scaffold(
        contentWindowInsets = WindowInsets.safeGestures,
        topBar = {
            TopAppBar(windowInsets = AppBarDefaults.topAppBarWindowInsets, title = {
                Text(stringResource(R.string.CreateShiftHeadline))
            }, actions = {
                //Gehe Zurück zur Listenansicht, wenn zurückbutton geklickt
                IconButton(onClick = { onNavigateToList() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, "go Back", tint = Color.White
                    )
                }
            })

        }) { padding ->
        //Zeige Start- und Enddatum mit Uhrzeiten
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            //Erzeuge neue Schicht mit default-Daten
            Shift(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                LocalDateTime.now().plusHours(4).truncatedTo(ChronoUnit.MINUTES)
            )

            //DateTimePicker für Start der Schicht
            DateTimePicker(
                headerText = stringResource(R.string.CreateShiftChooseBegin),
                startDefault = state.startDate,
                onOkButtonClick = {
                    viewModel.addEvent(CreateShiftEvent.StartDateChanged(it))
                },
                dateDialogState = startDialogState
            )

            //DateTimePicker für Ende der Schicht
            DateTimePicker(
                headerText = stringResource(R.string.CreateShiftChooseEnd),
                startDefault = state.endDate,
                onOkButtonClick = {
                    viewModel.addEvent(CreateShiftEvent.EndDateChanged(it))

                },
                dateDialogState = endDialogState
            )

            Text(
                text = stringResource(R.string.CreateShiftBeginHeadline),
                fontWeight = FontWeight.ExtraBold
            )

            //Zeige Schichtanfang
            Text(
                text = state.startDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable { startDialogState.show() }
                    .padding(6.dp))

            Spacer(modifier = Modifier.height(64.dp))

            Text(
                text = stringResource(R.string.CreateShiftEndHeadline),
                fontWeight = FontWeight.ExtraBold
            )

            //Zeige Schichtende
            Text(
                text = state.endDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)),
                fontSize = 32.sp,
                modifier = Modifier
                    .clickable { endDialogState.show() }
                    .padding(6.dp))

            Spacer(modifier = Modifier.height(16.dp))

            //Button zum Eintragen der neuen Schicht
            Button(
                onClick = {
                    viewModel.addEvent(CreateShiftEvent.CreateShift())
                }, modifier = Modifier
                    .padding(6.dp)
                    .fillMaxWidth()
            ) {
                Text(stringResource(R.string.CreateShiftButtonText))
            }
        }
    }
}