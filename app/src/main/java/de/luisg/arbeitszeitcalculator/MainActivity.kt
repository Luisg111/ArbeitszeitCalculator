package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.data.repository.RoomShiftRepository
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateListView
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateSetDateView
import de.luisg.arbeitszeitcalculator.viewmodel.ShiftRepository
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    private val addr =
        "https://calendar.google.com/calendar/ical/52sbtq3idh46n9eveh1h05glf8%40group.calendar.google.com/private-8a821bd1dd1043181ee2e190ccecc8dc/basic.ics"
    private lateinit var repo: ShiftRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = RoomShiftRepository(this)
        repo.addShift(
            Shift(
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(2)
            )
        )
        setContent {
            HandleUI()
        }
    }

    @Composable
    fun HandleUI() {
        val navController = rememberNavController()

        var month = remember {
            LocalDateTime.now().month
        }
        var year = remember {
            LocalDateTime.now().year
        }

        MaterialTheme {
            NavHost(navController = navController, startDestination = "config") {
                composable("config") {
                    GenerateSetDateView(
                        { newYear -> year = newYear },
                        { newMonth -> month = Month.of(newMonth) },
                        { navController.navigate("list") },
                        year,
                        month,
                    )
                }
                composable("list") {
                    val items by repo.getShiftsForYearMonth(year, month).collectAsState(emptyList())
                    GenerateListView(
                        shifts = items,
                        {
                            var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
                            items.forEach { shift ->
                                sum = sum.plus(shift.getShiftDuration())
                            }
                            return@GenerateListView sum
                        }
                    ) { navController.navigate("config") }
                }
            }
        }
    }
}