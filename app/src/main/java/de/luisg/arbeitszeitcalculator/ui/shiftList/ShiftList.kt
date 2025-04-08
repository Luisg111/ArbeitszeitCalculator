package de.luisg.arbeitszeitcalculator.ui.shiftList

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.domain.util.ShiftOrder
import de.luisg.arbeitszeitcalculator.ui.shiftSettings.createShiftSettings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import java.time.LocalDateTime

@Composable
fun GenerateShiftListView(
    navController: NavController,
) {
    val shiftUseCases = koinInject<ShiftUseCases>()
    val loanUseCases = koinInject<LoanUseCases>()

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
        topBar = {
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("create") },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(Icons.Filled.Add, "")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp)
        ) {
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
                        navController = navController
                    )
                }
            }
        }
    }
}