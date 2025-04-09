package de.luisg.arbeitszeitcalculator.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import de.luisg.arbeitszeitcalculator.ui.createShiftScreen.CreateShiftRoot
import de.luisg.arbeitszeitcalculator.ui.icalImportScreen.IcalImportRoot
import de.luisg.arbeitszeitcalculator.ui.shiftListScreen.ShiftListRoot
import kotlinx.serialization.Serializable

@Composable
fun UiNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    //NavHost für die Navigation
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = ShiftListScreen,
    ) {
        composable<ShiftListScreen> {
            //Listenansicht bestehender Schichten
            ShiftListRoot(
                onNavigateToNewShift = { navController.navigate(ShiftDetailsScreen()) },
                onNavigateToIcalImport = {
                    navController.navigate(IcalImportScreen)
                },
                onNavigateToShift = {
                    navController.navigate(ShiftDetailsScreen(shiftId = it))
                })
        }
        composable<ShiftDetailsScreen> {
            val args = it.toRoute<ShiftDetailsScreen>()
            //Schicht erstellen oder aktualisieren
            CreateShiftRoot(
                onNavigateToList = { navController.navigate(ShiftListScreen) },
                shiftId = args.shiftId,
            )

        }
        composable<IcalImportScreen> {
            //Schichten Importieren
            IcalImportRoot(
                onNavigateToList = { navController.navigate(ShiftListScreen) })
        }
    }
}

@Serializable
object ShiftListScreen

@Serializable
data class ShiftDetailsScreen(
    val shiftId: Int? = null
)

@Serializable
object IcalImportScreen
