package de.luisg.arbeitszeitcalculator.ui.show_shifts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDateTime

@Composable
fun CreateFilterSettings(
    onDateUpdate: (year: Int, month: Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var month by remember { mutableStateOf(LocalDateTime.now().month.value) }
    var year by remember { mutableStateOf(LocalDateTime.now().year) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5F)
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
                            year = newYear
                            onDateUpdate(year, month)
                        }
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 32.sp),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None
                )
            }
        }
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
                .padding(32.dp)
        ) {
            Column {
                Text(text = "Monat")
                Text(
                    text = month.toString(),
                    fontSize = 32.sp,
                    modifier = Modifier.clickable { expanded = true })
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    for (i in 1..12) {
                        DropdownMenuItem(onClick = {
                            month = i
                            expanded = false
                            onDateUpdate(year, month)
                        }) {
                            Text(i.toString())
                        }
                    }
                }
            }
        }
    }
}