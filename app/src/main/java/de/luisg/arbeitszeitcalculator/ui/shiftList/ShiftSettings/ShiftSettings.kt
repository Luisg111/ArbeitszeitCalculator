package de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftSettings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases.LoanUseCases

@Composable
fun createShiftSettings(
    loanUseCases: LoanUseCases
) {
    var maxHoursMonth by remember { mutableStateOf(TextFieldValue("450")) }
    var showSettings by remember { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Schichteinstellungen",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    showSettings = !showSettings
                }

        )
        if (showSettings) {
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = loanUseCases.getLoan().toString(),
                onValueChange = {
                    try {
                        loanUseCases.setLoan(it.toDouble())
                    } catch (e: NumberFormatException) {
                        Toast.makeText(context, "Nummer Parse Error!", Toast.LENGTH_SHORT).show()
                    }
                },
                label = { Text("Stundenlohn (€)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = maxHoursMonth,
                onValueChange = { maxHoursMonth = it },
                label = { Text("Monatslimit (€)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}