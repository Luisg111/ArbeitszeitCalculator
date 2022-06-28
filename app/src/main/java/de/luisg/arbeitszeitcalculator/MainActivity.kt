package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.luisg.arbeitszeitcalculator.ui.create_shift.CreateShiftView
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateShiftListView
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl.RoomShiftRepository

class MainActivity : ComponentActivity() {
    private lateinit var repo: ShiftRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Repo erstellen
        repo = RoomShiftRepository(this)
        setContent {
            HandleUI()
        }
    }

    @Composable
    fun HandleUI() {
        //NavController erstellen für Seitennavigation
        val navController = rememberNavController()

        MaterialTheme(
            colors = MaterialTheme.colors.copy(
                primary = Color(70, 120, 255)
            )
        ) {
            //NavHost für die Navigation
            NavHost(navController = navController, startDestination = "list") {
                composable("list") {
                    //Listenansicht bestehender Schichten
                    GenerateShiftListView(
                        repo = repo,
                        navController = navController
                    )
                }
                composable("create") {
                    //Neue Schicht eintragen
                    CreateShiftView(
                        repo = repo,
                        navController = navController
                    )
                }
            }
        }
    }
}