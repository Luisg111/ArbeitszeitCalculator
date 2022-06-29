package de.luisg.arbeitszeitcalculator.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.luisg.arbeitszeitcalculator.ui.UiNavHost
import de.luisg.arbeitszeitcalculator.viewmodel.Importer.impl.IcalImporter
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl.RoomShiftRepository

class MainController : ComponentActivity() {
    private lateinit var repo: ShiftRepository
    private lateinit var navHostController: NavHostController
    private var icalImporter = IcalImporter(repo)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Repo erstellen
        repo = RoomShiftRepository(this)
        setContent {
            createUI()
        }
    }

    @Composable
    private fun createUI() {
        navHostController = rememberNavController()
        UiNavHost(
            navController = navHostController,
            repo = repo,
            importer = icalImporter
        )
    }
}