package de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CreateFilterSettings(
    onDateUpdate: (year: Int, month: Int) -> Unit,
    year: Int,
    month: Int,
    monthExpanded: Boolean,
    monthSelectorToggled: (isOpen: Boolean) -> Unit,
) {

    val yearFocus = remember { FocusRequester() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        //Box für Jahresauswahl
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .clickable { yearFocus.requestFocus() }
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(32.dp)
        ) {
            Column {
                Text(text = "Jahr")
                BasicTextField(
                    value = year.toString(), onValueChange = {
                        val newYear = it.toIntOrNull()
                        if (newYear != null && newYear > 0) {
                            onDateUpdate(newYear, month)
                        }
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 32.sp),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                    ),
                    modifier = Modifier.focusRequester(yearFocus)
                )
            }
        }
        //Box für Monatsauswahl
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { monthSelectorToggled(true) }
                .background(Color.LightGray)
                .padding(32.dp)
        ) {
            Column {
                Text(text = "Monat")
                Text(
                    text = month.toString(),
                    fontSize = 32.sp,
                    modifier = Modifier.clickable { monthSelectorToggled(true) })
                DropdownMenu(
                    expanded = monthExpanded,
                    onDismissRequest = { monthSelectorToggled(false) },
                ) {
                    for (i in 1..12) {
                        DropdownMenuItem(onClick = {
                            onDateUpdate(year, i)
                            monthSelectorToggled(false)
                        }) {
                            Text(i.toString())
                        }
                    }
                }
            }
        }
    }
}