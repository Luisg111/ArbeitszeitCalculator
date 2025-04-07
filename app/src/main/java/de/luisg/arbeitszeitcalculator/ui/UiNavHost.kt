package de.luisg.arbeitszeitcalculator.ui

import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.luisg.arbeitszeitcalculator.ui.createShift.CreateShiftView
import de.luisg.arbeitszeitcalculator.ui.icalImportScreen.IcalImportScreen
import de.luisg.arbeitszeitcalculator.ui.shiftList.GenerateShiftListView
import de.luisg.arbeitszeitcalculator.ui.updateShift.UpdateShift

@Composable
fun UiNavHost() {
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
                    navController = navController,
                )
            }
            composable("create") {
                //Neue Schicht eintragen
                CreateShiftView(
                    navController = navController
                )
            }
            composable(
                "update/{shiftId}",
                arguments = listOf(navArgument("shiftId") { type = NavType.IntType })
            ) {
                //Schicht aktualisieren
                if (it.arguments != null) {
                    UpdateShift(
                        id = it.arguments!!.getInt("shiftId"),
                        navController = navController
                    )
                } else {
                    Toast.makeText(
                        LocalContext.current,
                        "Schicht konnte nicht geladen werden!",
                        Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate("list")
                }
            }
            composable("import_ical") {
                //Schichten Importieren
                IcalImportScreen(
                    navController = navController,
                )
            }
        }
    }
}