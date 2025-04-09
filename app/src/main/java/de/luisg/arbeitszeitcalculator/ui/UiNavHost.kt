package de.luisg.arbeitszeitcalculator.ui

import android.widget.Toast

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import de.luisg.arbeitszeitcalculator.ui.createShiftScreen.CreateShiftRoot
import de.luisg.arbeitszeitcalculator.ui.icalImportScreen.IcalImportRoot
import de.luisg.arbeitszeitcalculator.ui.shiftListScreen.ShiftListRoot

@Composable
fun UiNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    //NavHost für die Navigation
    NavHost(
        navController = navController,
        startDestination = "list",
        modifier = modifier,
    ) {
        composable("list") {
            //Listenansicht bestehender Schichten
            ShiftListRoot(
                onNavigateToNewShift = { navController.navigate("create") },
                onNavigateToIcalImport = {
                    navController.navigate("import_ical")
                },
                onNavigateToShift = {
                    navController.navigate("update/${it}")
                })
        }
        composable("create") {
            //Neue Schicht eintragen
            CreateShiftRoot(
                onNavigateToList = { navController.navigate("list") },
                shiftId = null,
            )
        }
        composable(
            "update/{shiftId}",
            arguments = listOf(navArgument("shiftId") { type = NavType.IntType })
        ) {
            //Schicht aktualisieren
            if (it.arguments != null) {
                CreateShiftRoot(
                    onNavigateToList = { navController.navigate("list") },
                    shiftId = it.arguments!!.getInt("shiftId"),
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
            IcalImportRoot(
                onNavigateToList = { navController.navigate("list") })
        }
    }

}