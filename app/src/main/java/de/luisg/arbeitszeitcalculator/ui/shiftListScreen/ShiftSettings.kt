package de.luisg.arbeitszeitcalculator.ui.shiftListScreen

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ShiftSettings(
    maxHoursMonth: TextFieldValue,
    loan: TextFieldValue,
    settingsExtended: Boolean,
    onSettingsToggled: () -> Unit,
    onSallaryChanged: (value: TextFieldValue) -> Unit,
    onMaxHoursChanged: (value: TextFieldValue) -> Unit,
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
            TextField(
                value = loan,
                onValueChange = {
                    onSallaryChanged(it)
                },
                label = { Text("Stundenlohn (€)") },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            TextField(
                value = maxHoursMonth,
                onValueChange = { onMaxHoursChanged(it) },
                label = { Text("Monatslimit (€)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }
    }
}