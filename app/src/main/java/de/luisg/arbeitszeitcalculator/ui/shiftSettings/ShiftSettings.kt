package de.luisg.arbeitszeitcalculator.ui.shiftSettings

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases

@Composable
fun createShiftSettings(
    loanUseCases: LoanUseCases
) {
    var maxHoursMonth by remember { mutableStateOf(TextFieldValue("450")) }
    var showSettings by remember { mutableStateOf(false) }

    var loan by remember { mutableStateOf(TextFieldValue(loanUseCases.getLoan().toString())) }

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
                value = loan,
                onValueChange = {
                    loan = it
                },
                label = { Text("Stundenlohn (€)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        if (!it.hasFocus) {
                            try {
                                loanUseCases.setLoan(loan.text.toDouble())
                            } catch (e: NumberFormatException) {
                                Toast
                                    .makeText(
                                        context,
                                        "Nummer kann nicht gelesen werden!",
                                        Toast.LENGTH_SHORT
                                    )
                                    .show()
                            }
                        }
                    },
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