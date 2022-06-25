package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.luisg.arbeitszeitcalculator.data.repository.RoomShiftRepository
import de.luisg.arbeitszeitcalculator.ui.create_shift.CreateShiftView
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateSetDateView
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateShiftListView
import de.luisg.arbeitszeitcalculator.viewmodel.ShiftRepository
import java.time.Duration
import java.time.LocalDateTime
import java.time.Month
import java.time.temporal.ChronoUnit

class MainActivity : ComponentActivity() {
    private lateinit var repo: ShiftRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repo = RoomShiftRepository(this)
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
                    Scaffold(floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navController.navigate("create") },
                            backgroundColor = Color.Blue,
                            contentColor = Color.White
                        ) {
                            Icon(Icons.Filled.Add, "")
                        }
                    }) {
                        GenerateSetDateView(
                            { newYear -> year = newYear },
                            { newMonth -> month = Month.of(newMonth) },
                            { navController.navigate("list") },
                            year,
                            month,
                        )
                    }
                }
                composable("list") {
                    val items by repo.getShiftsForYearMonth(year, month.value)
                        .collectAsState(emptyList())
                    GenerateShiftListView(
                        shifts = items,
                        total = {
                            var sum: Duration = Duration.of(0, ChronoUnit.MINUTES)
                            items.forEach { shift ->
                                sum = sum.plus(shift.getShiftDuration())
                            }
                            return@GenerateShiftListView sum
                        },
                        onBackButtonPressed = { navController.navigate("config") },
                        deleteAction = { repo.removeShift(it) }
                    )
                }
                composable("create") {
                    CreateShiftView({
                        repo.addShift(it)
                    }, {
                        navController.navigate("config")
                    })
                }
            }
        }
    }
}