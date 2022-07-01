package de.luisg.arbeitszeitcalculator.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import de.luisg.arbeitszeitcalculator.ui.UiNavHost
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.Repository.impl.RoomShiftRepository
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases.ShiftUseCases

class MainController : ComponentActivity() {
    private lateinit var repo: ShiftRepository
    private lateinit var navHostController: NavHostController
    private lateinit var shiftUseCases: ShiftUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Repo erstellen
        repo = RoomShiftRepository(this)
        shiftUseCases = ShiftUseCases(repository = repo)
        setContent {
            CreateUI()
        }
    }

    @Composable
    private fun CreateUI() {
        navHostController = rememberNavController()
        UiNavHost(
            navController = navHostController,
            shiftUseCases = shiftUseCases
        )
    }
}