package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.data.Shift
import java.time.Duration
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun GenerateListView(
    shifts: List<Shift>,
    total: () -> Duration,
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
        GenerateShiftView(shifts, total())
    }
}

@Composable
private fun GenerateShiftView(shifts: List<Shift>, total: Duration) {
    LazyColumn(Modifier.padding(16.dp)) {
        items(items = shifts) { item ->
            Box(
                modifier = Modifier
                    .padding(horizontal = 0.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray)

            ) {
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
            }
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