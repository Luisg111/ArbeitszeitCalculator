package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.luisg.arbeitszeitcalculator.ui.theme.ArbeitszeitCalculatorTheme
import de.luisg.arbeitszeitcalculator.viewmodel.ShiftHandler

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        ShiftHandler("https://calendar.google.com/calendar/ical/52sbtq3idh46n9eveh1h05glf8%40group.calendar.google.com/private-8a821bd1dd1043181ee2e190ccecc8dc/basic.ics").updateData()
        super.onCreate(savedInstanceState)
        setContent {
            ArbeitszeitCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
        }
    }
}