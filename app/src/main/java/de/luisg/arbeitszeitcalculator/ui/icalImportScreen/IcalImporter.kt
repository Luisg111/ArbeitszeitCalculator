package de.luisg.arbeitszeitcalculator.ui.icalImportScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import de.luisg.arbeitszeitcalculator.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun IcalImportRoot(
    onNavigateToList: () -> Unit,
) {
    val viewModel: IcalImporterViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.closeWindow == true) {
        onNavigateToList()
    }

    IcalImportView(
        viewModel = viewModel, state = state, onBackButtonPressed = { onNavigateToList() })
}

@Composable
fun IcalImportView(
    viewModel: IcalImporterViewModel, state: IcalImporterState, onBackButtonPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.IcalImporterHeadline))
            }, actions = {
                //Gehe Zurück zur Listenansicht, wenn zurückbutton geklickt
                IconButton(onClick = { onBackButtonPressed() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack, "go Back", tint = Color.White
                    )
                }
            })
        },
        modifier = Modifier.fillMaxSize()
    ) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(padding)

        ) {
            LocalContext.current

            //URL Eingabefeld
            TextField(
                label = { Text("url zur ical") },
                value = state.url,
                onValueChange = { viewModel.addEvent(IcalImporterEvent.UrlChanged(it)) })

            //Button zum Auslösen des Updates
            Button(onClick = {
                viewModel.addEvent(IcalImporterEvent.ImportStarted())
            }) {
                Text("Ical Herunterladen & Importieren")
            }
        }
    }
}