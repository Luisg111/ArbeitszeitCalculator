package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.luisg.arbeitszeitcalculator.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShiftListRoot(
    onNavigateToIcalImport: () -> Unit,
    onNavigateToNewShift: () -> Unit,
    onNavigateToShift: (Int) -> Unit,
) {
    val viewModel: ShiftListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    ShiftListView(
        onNavigateToShift = { onNavigateToShift(it) },
        onNavigateToNewShift = { onNavigateToNewShift() },
        onNavigateToIcalImport = { onNavigateToIcalImport() },
        viewModel = viewModel,
        state = state,
    )
}

@Composable
fun ShiftListView(
    onNavigateToIcalImport: () -> Unit,
    onNavigateToNewShift: () -> Unit,
    onNavigateToShift: (id: Int) -> Unit,
    viewModel: ShiftListViewModel,
    state: ShiftListState,
) {
    val listItems by viewModel.listItems.collectAsStateWithLifecycle()

    val exportJsonLauncher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/json"), onResult = { uri ->
            if (uri != null) {
                viewModel.addEvent(ShiftListEvent.ExportToJson(uri))
            }
        })

    val importJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), onResult = { uri ->
            if (uri != null) {
                viewModel.addEvent(ShiftListEvent.ImportFromJson(uri))
            }
        })


    //Scaffold für Action Button
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(title = {
            Text(stringResource(R.string.ShiftListHeadline))
        }, actions = {
            IconButton(onClick = { viewModel.addEvent(ShiftListEvent.IoMenuToggled()) }) {
                Icon(Icons.Filled.MoreVert, "more...")
            }
            DropdownMenu(
                expanded = state.ioMenuExtended,
                onDismissRequest = { viewModel.addEvent(ShiftListEvent.IoMenuToggled(false)) }) {
                DropdownMenuItem(onClick = { onNavigateToIcalImport() }) {
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
                    viewModel.addEvent(ShiftListEvent.DeleteAllShifts())
                }) {
                    Text("Alle Schichten löschen")
                }
            }
        })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { onNavigateToNewShift() }, backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(top = 16.dp)
        ) {
            //Filtereinstellungen
            CreateFilterSettings(
                startYear = state.year, startMonth = state.month, onDateUpdate = { nYear, nMonth ->
                    viewModel.addEvent(ShiftListEvent.SelectedYearChanged(nYear))
                    viewModel.addEvent(ShiftListEvent.SelectedMonthChanged(nMonth))
                })

            Spacer(Modifier.height(16.dp))
            ShiftSettings(
                maxHoursMonth = TextFieldValue(state.maxHours.toString()),
                loan = TextFieldValue(state.sallary.toString()),
                settingsExtended = state.settingsExtended,
                onSettingsToggled = { viewModel.addEvent(ShiftListEvent.SettingsMenuToggled()) },
                onSallaryChanged = {
                    viewModel.addEvent(
                        ShiftListEvent.SallaryChanged(
                            it.toString().toDouble()
                        )
                    )
                },
                onMaxHoursChanged = {
                    viewModel.addEvent(
                        ShiftListEvent.MaxHoursChanged(
                            it.toString().toInt()
                        )
                    )
                },
            )
            Spacer(Modifier.height(16.dp))

            //Liste mit Schichten und Gesamtdauer am Ende
            MonthTotal(
                items = listItems
            )
            Spacer(Modifier.height(16.dp))

            //Alle Schichten anzeigen
            LazyColumn(Modifier.padding(horizontal = 16.dp, vertical = 0.dp)) {
                items(listItems, { shift -> shift.id!! }) { item ->
                    CreateShiftListItem(
                        item = item,
                        backgroundColor = MaterialTheme.colors.primary,
                        foregroundColor = MaterialTheme.colors.onPrimary,
                        onNavigateToShift = { onNavigateToShift(it) })
                }
            }
        }
    }
}