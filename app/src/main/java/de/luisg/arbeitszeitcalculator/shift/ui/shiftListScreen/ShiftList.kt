package de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.CreateDocument
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.AppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.components.CreateFilterSettings
import de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.components.CreateShiftListItem
import de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.components.MonthTotal
import de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.components.ShiftSettings
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
        state = state,
        onEvent = viewModel::addEvent,
    )
}

@Composable
fun ShiftListView(
    onNavigateToIcalImport: () -> Unit,
    onNavigateToNewShift: () -> Unit,
    onNavigateToShift: (id: Int) -> Unit,
    state: ShiftListState,
    onEvent: (ShiftListEvent) -> Unit,
) {

    val exportJsonLauncher = rememberLauncherForActivityResult(
        contract = CreateDocument("application/json"), onResult = { uri ->
            if (uri != null) {
                onEvent(ShiftListEvent.ExportToJson(uri))
            }
        })

    val importJsonLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(), onResult = { uri ->
            if (uri != null) {
                onEvent(ShiftListEvent.ImportFromJson(uri))
            }
        })


    //Scaffold für Action Button
    Scaffold(contentWindowInsets = WindowInsets.safeDrawing, topBar = {
        TopAppBar(windowInsets = AppBarDefaults.topAppBarWindowInsets, title = {
            Text(
                stringResource(R.string.ShiftListHeadline),
            )
        }, actions = {
            IconButton(onClick = { onEvent(ShiftListEvent.IoMenuToggled()) }) {
                Icon(Icons.Filled.MoreVert, "more...")
            }
            DropdownMenu(
                expanded = state.ioMenuExtended,
                onDismissRequest = { onEvent(ShiftListEvent.IoMenuToggled(false)) }) {
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
                    onEvent(ShiftListEvent.DeleteAllShifts())
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
                year = state.year,
                month = state.month,
                onDateUpdate = { nYear, nMonth ->
                    onEvent(ShiftListEvent.SelectedYearChanged(nYear))
                    onEvent(ShiftListEvent.SelectedMonthChanged(nMonth))
                }, monthExpanded = state.monthMenuOpen, monthSelectorToggled = {
                    onEvent(ShiftListEvent.MonthMenuToggled())
                })

            Spacer(Modifier.height(16.dp))
            ShiftSettings(
                salary = state.salary,
                settingsExtended = state.settingsExtended,
                onSettingsToggled = { onEvent(ShiftListEvent.SettingsMenuToggled()) },
                onSalaryChanged = {
                    onEvent(
                        ShiftListEvent.SalaryChanged(
                            it
                        )
                    )
                },
                salaryError = state.salaryError
            )
            Spacer(Modifier.height(16.dp))

            //Liste mit Schichten und Gesamtdauer am Ende
            MonthTotal(
                itemsVisible = state.monthOverviewExtended,
                onItemsVisibilityToggled = {
                    onEvent(
                        ShiftListEvent.MonthOverviewToggled(
                            it
                        )
                    )
                },
                shiftDurationString = state.hourSummaryString,
                salaryString = state.salarySummaryString,
            )
            Spacer(Modifier.height(16.dp))

            //Alle Schichten anzeigen
            LazyColumn(Modifier.padding(horizontal = 16.dp, vertical = 0.dp)) {
                items(state.listItems, { shift -> shift.id!! }) { item ->
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