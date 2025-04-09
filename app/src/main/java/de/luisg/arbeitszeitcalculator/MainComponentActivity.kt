package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.luisg.arbeitszeitcalculator.ui.UiNavHost
import de.luisg.myapplication.ui.theme.ArbeitszeitCalculatorTheme

class MainComponentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CreateUI()
        }
    }

    @Composable
    private fun CreateUI() {
        ArbeitszeitCalculatorTheme {
            Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            }) { innerPadding ->
                UiNavHost(modifier = Modifier.padding(innerPadding))
            }
        }
    }
}