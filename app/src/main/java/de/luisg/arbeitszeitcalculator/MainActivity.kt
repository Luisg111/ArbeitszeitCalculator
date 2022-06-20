package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateAppBar
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateConfigMenu
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateShiftView
import de.luisg.arbeitszeitcalculator.viewmodel.ShiftHandler
import java.time.LocalDateTime
import java.time.Month
import java.time.Year

class MainActivity : ComponentActivity() {
    private val addr =
        "https://calendar.google.com/calendar/ical/52sbtq3idh46n9eveh1h05glf8%40group.calendar.google.com/private-8a821bd1dd1043181ee2e190ccecc8dc/basic.ics"
    private val handler = ShiftHandler(addr)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HandleUI()
        }
    }

    @Composable
    fun HandleUI() {
        var config by remember {
            mutableStateOf(true)
        }
        var month = remember {
            LocalDateTime.now().month
        }
        var year = remember {
            LocalDateTime.now().year
        }

        MaterialTheme {
            Column() {
                if (config) {
                    GenerateAppBar("Monat/Jahr auswählen") { config = false }
                    GenerateConfigMenu({ newYear -> year = newYear },
                        { newMonth -> month = Month.of(newMonth) })
                } else {
                    GenerateAppBar("Schichtübersicht") { config = true }
                    GenerateShiftView(handler.getShiftsByYearMonth(Year.of(year), month))
                }
            }
        }
    }
}