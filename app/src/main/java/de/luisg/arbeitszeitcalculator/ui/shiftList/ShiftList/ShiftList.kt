package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftList.CreateFilterSettings
import de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftSettings.createShiftSettings
import de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftList.MonthTotal
import de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftList.CreateShiftListItem
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.domain.util.ShiftOrder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun GenerateShiftListView(
    navController: NavController,
    shiftUseCases: ShiftUseCases,
    loanUseCases: LoanUseCases
) {
    val exportJsonLauncher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/json"),
        onResult = { uri ->
            if (uri != null) {
                MainScope().launch(Dispatchers.IO) {

                    shiftUseCases.exportShiftToJson(
                        shiftUseCases.getShift(ShiftOrder.Ascending),
                        uri
                    )
                }
            }
        }
    )

    val importJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            if (uri != null) {
                MainScope().launch(Dispatchers.IO) {
                    shiftUseCases.importShiftFromJson(uri)?.forEach {
                        shiftUseCases.storeShift(it)
                    }
                }
            }
        }
    )

    var month by remember {
        mutableStateOf(LocalDateTime.now().month.value)
    }
    var year by remember {
        mutableStateOf(LocalDateTime.now().year)
    }

    var ioMenuExtended by remember { mutableStateOf(false) }

    val items by shiftUseCases.getShiftLive(
        order = ShiftOrder.Descending,
        year = year,
        month = month
    ).collectAsState(initial = emptyList())

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
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            //App Bar mit Text
            TopAppBar(
                title = {
                    Text(stringResource(R.string.ShiftListHeadline))
                },
                actions = {
                    IconButton(onClick = { ioMenuExtended = !ioMenuExtended }) {
                        Icon(Icons.Filled.MoreVert, "more...")
                    }
                    DropdownMenu(
                        expanded = ioMenuExtended,
                        onDismissRequest = { ioMenuExtended = false }
                    ) {
                        DropdownMenuItem(onClick = { navController.navigate("import_ical") }) {
                            Text("Import Ical")
                        }
                        DropdownMenuItem(onClick = {
                            importJsonLauncher.launch(Array(1) { "application/json" })
                        }) {
                            Text("Import JSON")
                        }
                        DropdownMenuItem(onClick = { exportJsonLauncher.launch("ShiftExport") }) {
                            Text("Export JSON")
                        }
                        DropdownMenuItem(onClick = {
                            MainScope().launch(Dispatchers.IO) {
                                shiftUseCases.deleteShift()
                            }
                        }) {
                            Text("Alle Schichten löschen")
                        }
                    }
                }
            )

            Spacer(Modifier.height(16.dp))

            //Filtereinstellungen
            CreateFilterSettings(
                startYear = year,
                startMonth = month,
                onDateUpdate = { nYear, nMonth ->
                    month = nMonth
                    year = nYear
                })

            Spacer(Modifier.height(16.dp))
            createShiftSettings(loanUseCases)
            Spacer(Modifier.height(16.dp))

            //Liste mit Schichten und Gesamtdauer am Ende
            MonthTotal(
                shiftUseCases = shiftUseCases,
                loanUseCases = loanUseCases,
                items = items
            )
            Spacer(Modifier.height(16.dp))

            //Alle Schichten anzeigen
            LazyColumn(Modifier.padding(horizontal = 16.dp, vertical = 0.dp)) {
                items(items, { shift -> shift.id!! }) { item ->
                    CreateShiftListItem(
                        item = item,
                        backgroundColor = MaterialTheme.colors.primary,
                        foregroundColor = MaterialTheme.colors.onPrimary,
                        shiftUseCases = shiftUseCases,
                        navController = navController
                    )
                }
            }
        }
    }
}