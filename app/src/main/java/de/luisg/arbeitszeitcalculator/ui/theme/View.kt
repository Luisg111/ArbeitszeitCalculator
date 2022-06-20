package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.luisg.arbeitszeitcalculator.model.Shift

@Composable
fun GenerateShiftView(shifts: List<Shift>) {
    LazyColumn() {
        items(items = shifts) { item ->
            Text(
                text = "${item.startDateTime} - ${item.endDateTime}",
                modifier = Modifier.padding(6.dp)
            )
        }
    }
}

@Composable
fun GenerateAppBar(text: String, onBackButtonPressed: () -> Unit) {
    TopAppBar(title = {
        Text(text)
    },
        actions = {
            IconButton(onClick = onBackButtonPressed) {
                Icon(
                    Icons.Filled.ArrowBack,
                    "go Back",
                    tint = Color.White
                )
            }
        })
}

@Composable
fun GenerateConfigMenu(
    setYear: (year: Int) -> Unit,
    setMonth: (month: Int) -> Unit,
) {
    var year = "2022"
    var month = "6"
    Column {
        TextField(value = year, onValueChange = {
            try {
                year = it
                setYear(it.toInt())
            } catch (e: NumberFormatException) {

            }
        })
        TextField(value = month, onValueChange = {
            try {
                month = it
                setMonth(it.toInt())
            } catch (e: NumberFormatException) {

            }
        })
    }
}