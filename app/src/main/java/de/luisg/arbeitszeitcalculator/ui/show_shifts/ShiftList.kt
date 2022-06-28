package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.ui.show_shifts.CreateFilterSettings
import de.luisg.arbeitszeitcalculator.ui.show_shifts.CreateShiftListItem
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import java.time.Duration
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Composable
fun GenerateShiftListView(
    repo: ShiftRepository,
    navController: NavController
) {
    var month by remember {
        mutableStateOf(LocalDateTime.now().month.value)
    }
    var year by remember {
        mutableStateOf(LocalDateTime.now().year)
    }

    val items by repo.getShiftsForYearMonth(year, month)
        .collectAsState(emptyList())

    //Scaffold für Action Button
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) {
        Column() {
            //App Bar mit Text
            TopAppBar(
                title = {
                    Text(stringResource(R.string.ShiftListHeadline))
                },
            )

            Spacer(Modifier.height(16.dp))

            //Filtereinstellungen
            CreateFilterSettings { nYear, nMonth ->
                month = nMonth
                year = nYear
            }

            Spacer(Modifier.height(16.dp))

            //Liste mit Schichten und Gesamtdauer am Ende
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
            ) {
                //Monatssume berechnen
                var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
                items.forEach { shift ->
                    sum = sum.plus(shift.getShiftDuration())
                }

                //Monatssume ausgeben
                Text(stringResource(R.string.ShiftListMonthTotalLabel))
                Text(
                    "${sum.toHours()} h ${
                        (sum.toMinutes() % 60).toString().padStart(2, '0')
                    } min"
                )
            }

            Spacer(Modifier.height(16.dp))

            //Alle Schichten anzeigen
            LazyColumn(Modifier.padding(horizontal = 16.dp, vertical = 0.dp)) {
                items(items, { shift -> shift.id!! }) { item ->
                    CreateShiftListItem(
                        item = item,
                        backgroundColor = MaterialTheme.colors.primary,
                        foregroundColor = MaterialTheme.colors.onPrimary,
                        deleteOperation = { repo.removeShift(it) },
                        updateOperation = { navController.navigate("update/${it.id}") })
                }
            }
        }
    }
}