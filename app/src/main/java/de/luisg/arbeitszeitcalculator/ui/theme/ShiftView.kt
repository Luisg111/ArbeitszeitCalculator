package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.model.Shift
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun GenerateListView(
    shifts: List<Shift>,
    total: Duration,
    onBackButtonPressed: () -> Unit
) {
    Column() {
        TopAppBar(title = {
            Text("Schichtübersicht")
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
        GenerateShiftView(shifts, total)
    }
}

@Composable
private fun GenerateShiftView(shifts: List<Shift>, total: Duration) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(items = shifts) { item ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Column() {
                    Text(
                        text =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                            .format(item.startDateTime.toLocalDate()),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        """${
                            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                .format(item.startDateTime.toLocalTime())
                        } - ${
                            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                .format(item.endDateTime.toLocalTime())
                        }"""
                    )
                }
                Text(
                    "${item.getShiftDuration().toHours()} h ${
                        (item.getShiftDuration().toMinutes() % 60).toString().padStart(2, '0')
                    } min"
                )
            }
            Divider(Modifier.padding(6.dp))
        }
        item {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillParentMaxWidth()
            ) {
                Text("Monatssume:")
                Text(
                    "${total.toHours()} h ${
                        (total.toMinutes() % 60).toString().padStart(2, '0')
                    } min"
                )
            }
        }
    }
}