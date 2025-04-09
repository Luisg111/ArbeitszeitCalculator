package de.luisg.arbeitszeitcalculator.ui.shiftListScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShiftSettings(
    salary: String,
    settingsExtended: Boolean,
    salaryError: Boolean,
    onSettingsToggled: () -> Unit,
    onSalaryChanged: (value: String) -> Unit,
) {
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
                    onSettingsToggled()
                }

        )
        if (settingsExtended) {
            Spacer(modifier = Modifier.height(6.dp))
            Column {
                TextField(
                    isError = salaryError,

                    value = salary,
                    onValueChange = {
                        onSalaryChanged(it)
                    },
                    label = { Text("Stundenlohn (€)") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                )
                if (salaryError) {
                    Text(
                        text = "Ungültige Eingabe!",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}