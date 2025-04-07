package de.luisg.arbeitszeitcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import de.luisg.arbeitszeitcalculator.ui.UiNavHost

class MainComponentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateUI()
        }
    }

    @Composable
    private fun CreateUI() {
        UiNavHost()
    }
}