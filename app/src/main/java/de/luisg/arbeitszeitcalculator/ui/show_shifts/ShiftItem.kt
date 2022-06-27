package de.luisg.arbeitszeitcalculator.ui.show_shifts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.luisg.arbeitszeitcalculator.data.Shift
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateShiftListItem(
    item: Shift,
    backgroundColor: Color,
    foregroundColor: Color,
    deleteOperation:(Shift)->Unit
    ) {
    val state = rememberDismissState()
    if (state.isDismissed(DismissDirection.StartToEnd) || state.isDismissed(
            DismissDirection.EndToStart
        )
    ) {
        deleteOperation(item)
    }
    val color by animateColorAsState(
        targetValue = when (state.targetValue) {
            DismissValue.DismissedToStart -> Color.Red
            DismissValue.DismissedToEnd -> Color.Red
            else -> backgroundColor
        }
    )
    SwipeToDismiss(
        state = state,
        background = {},
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
                        fontSize = 16.sp,
                        color = foregroundColor
                    )
                    Text(
                        """${
                            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                .format(item.startDateTime.toLocalTime())
                        } - ${
                            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
                                .format(item.endDateTime.toLocalTime())
                        }""",
                        color = foregroundColor
                    )
                }
                Text(
                    "${item.getShiftDuration().toHours()} h ${
                        (item.getShiftDuration().toMinutes() % 60).toString().padStart(2, '0')
                    } min",
                    color = foregroundColor
                )
            }
        }
    }
}