package de.luisg.arbeitszeitcalculator.ui.import_ical

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import biweekly.Biweekly
import biweekly.ICalendar
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun ImportIcal(
    repository: ShiftRepository,
    navController: NavController
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
                CoroutineScope(Dispatchers.IO).launch(Dispatchers.IO) {
                    var cal: ICalendar
                    BufferedInputStream(URL(url).openStream()).use {
                        cal = Biweekly.parse(it).first()
                    }
                    repository.getAllShifts().let {
                        println("new data")
                        for (event in cal.events) {
                            val shift = Shift(
                                LocalDateTime.ofInstant(
                                    event.dateStart.value.toInstant(),
                                    ZoneId.of("Europe/Berlin")
                                ),
                                LocalDateTime.ofInstant(
                                    event.dateEnd.value.toInstant(),
                                    ZoneId.of("Europe/Berlin")
                                )
                            )
                            if (!it.contains(shift)) {
                                repository.addShift(shift)
                            }
                        }
                        withContext(Dispatchers.Main) {
                            navController.navigate("list")
                        }
                    }
                }
            }) {
                Text("Ical Herunterladen & Importieren")
            }
        }
    }
}