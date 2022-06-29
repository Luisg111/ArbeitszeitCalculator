package de.luisg.arbeitszeitcalculator.ui

import android.widget.Toast
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import de.luisg.arbeitszeitcalculator.ui.create_shift.CreateShiftView
import de.luisg.arbeitszeitcalculator.ui.import_ical.ImportIcal
import de.luisg.arbeitszeitcalculator.ui.theme.GenerateShiftListView
import de.luisg.arbeitszeitcalculator.ui.update_shift.UpdateShift
import de.luisg.arbeitszeitcalculator.viewmodel.Importer.Importer
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository

@Composable
fun UiNavHost(
    navController: NavHostController,
    repo: ShiftRepository,
    importer: Importer
) {
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
            composable(
                "update/{shiftId}",
                arguments = listOf(navArgument("shiftId") { type = NavType.IntType })
            ) {
                //Schicht aktualisieren
                if (it.arguments != null) {
                    UpdateShift(
                        id = it.arguments!!.getInt("shiftId"),
                        repository = repo,
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
            composable("import") {
                //Schichten Importieren
                ImportIcal(
                    navController = navController,
                    importer = importer
                )
            }
        }
    }
}