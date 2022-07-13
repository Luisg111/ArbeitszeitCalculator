package de.luisg.arbeitszeitcalculator.ui.show_shifts

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview
fun createShiftSettings() {
    var loanValue = remember { mutableStateOf(TextFieldValue("11")) }
    var maxHoursMonth = remember { mutableStateOf(TextFieldValue("450")) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        TextField(
            value = loanValue.value,
            onValueChange = { loanValue.value = it },
            label = { Text("Stundenlohn (€)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
        Spacer(modifier = Modifier.height(6.dp))
        TextField(
            value = maxHoursMonth.value,
            onValueChange = { maxHoursMonth.value = it },
            label = { Text("Monatslimit (€)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}