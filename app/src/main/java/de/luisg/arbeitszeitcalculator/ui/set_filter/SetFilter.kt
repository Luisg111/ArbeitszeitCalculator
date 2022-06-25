package de.luisg.arbeitszeitcalculator.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.time.Month

@Composable
fun GenerateSetDateView(
    setYear: (year: Int) -> Unit,
    setMonth: (month: Int) -> Unit,
    onSubmit: () -> Unit,
    currentYear: Int,
    currentMonth: Month
) {
    Column() {
        TopAppBar(title = {
            Text("Monat/Jahr eingeben")
        })
        GenerateConfigMenu(
            setYear,
            setMonth,
            onSubmit,
            currentYear,
            currentMonth
        )
    }
}

@Composable
private fun GenerateConfigMenu(
    setYear: (year: Int) -> Unit,
    setMonth: (month: Int) -> Unit,
    onSubmit: () -> Unit,
    currentYear: Int,
    currentMonth: Month
) {
    var yearState by remember { mutableStateOf(currentYear.toString()) }
    var monthState by remember { mutableStateOf(currentMonth.value.toString()) }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = yearState,
            onValueChange = {
                yearState = it
            },
            label = { Text(text = "Jahr") },
            modifier = Modifier.padding(0.dp, 8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        TextField(
            value = monthState,
            onValueChange = {
                monthState = it
            },
            label = { Text(text = "Monat") },
            modifier = Modifier.padding(0.dp, 8.dp),
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.None,
                autoCorrect = false,
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onAny = {
                    val numYear = yearState.toIntOrNull()
                    val numMonth = monthState.toIntOrNull()
                    if (
                        (numYear != null && numMonth != null) &&
                        numYear > 0 && numMonth > 0 && numMonth <= 12
                    ) {
                        setYear(numYear)
                        setMonth(numMonth)
                        onSubmit()
                    } else {
                        Toast.makeText(context, "Invalide Eingabe!", Toast.LENGTH_LONG)
                            .show()
                    }
                }
            )

        )
        Button({
            val numYear = yearState.toIntOrNull()
            val numMonth = monthState.toIntOrNull()
            if (numYear != null && numMonth != null) {
                setYear(numYear)
                setMonth(numMonth)
            }
            onSubmit()
        }) {
            Text("Weiter")
        }
    }
}