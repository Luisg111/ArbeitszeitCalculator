package de.luisg.arbeitszeitcalculator.ui.import_ical

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases.ShiftUseCases
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@Composable
fun ImportIcal(
    navController: NavController,
    shiftUseCases: ShiftUseCases
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        //App bar mit Titel
        TopAppBar(
            title = {
                Text(stringResource(R.string.IcalImporterHeadline))
            },
            actions = {
                //Gehe Zurück zur Listenansicht, wenn zurückbutton geklickt
                IconButton(onClick = { navController.navigate("list") }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        "go Back",
                        tint = Color.White
                    )
                }
            }
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            var url by remember { mutableStateOf("https://calendar.google.com/calendar/ical/52sbtq3idh46n9eveh1h05glf8%40group.calendar.google.com/private-8a821bd1dd1043181ee2e190ccecc8dc/basic.ics") }
            val context = LocalContext.current

            //URL Eingabefeld
            TextField(
                label = { Text("url zur ical") },
                value = url,
                onValueChange = { url = it })

            //Button zum Auslösen des Updates
            Button(onClick = {
                MainScope().launch(Dispatchers.IO) {
                    shiftUseCases.importShiftFromIcal(url) { error ->
                        Toast.makeText(context, "Could not download ICal", Toast.LENGTH_LONG).show()
                    }
                }
                navController.navigate("list")
            }) {
                Text("Ical Herunterladen & Importieren")
            }
        }
    }
}