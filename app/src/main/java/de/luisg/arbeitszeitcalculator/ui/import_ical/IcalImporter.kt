package de.luisg.arbeitszeitcalculator.ui.import_ical

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.viewmodel.Importer.Importer

@Composable
fun ImportIcal(
    navController: NavController,
    importer: Importer
) {
    Column() {
        //App bar mit Titel
        TopAppBar(
            title = {
                Text(stringResource(R.string.IcalImporterHeadline))
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

        Column() {
            var url by remember { mutableStateOf("https://calendar.google.com/calendar/ical/52sbtq3idh46n9eveh1h05glf8%40group.calendar.google.com/private-8a821bd1dd1043181ee2e190ccecc8dc/basic.ics") }

            //URL Eingabefeld
            TextField(
                label = { Text("url zur ical") },
                value = url,
                onValueChange = { url = it })

            //Button zum Auslösen des Updates
            Button(onClick = {
                importer.import(url)
                navController.navigate("list")
            }) {
                Text("Ical Herunterladen & Importieren")
            }
        }
    }
}