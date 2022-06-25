package de.luisg.arbeitszeitcalculator.ui.show_shifts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.data.Shift
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun CreateShiftListItem(
    item: Shift,
    color: Color
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(color)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
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