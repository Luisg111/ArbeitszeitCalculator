package de.luisg.arbeitszeitcalculator.ui.theme

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.luisg.arbeitszeitcalculator.data.Shift
import de.luisg.arbeitszeitcalculator.ui.show_shifts.CreateShiftListItem
import java.time.Duration

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun GenerateShiftListView(
    shifts: List<Shift>,
    total: () -> Duration,
    onBackButtonPressed: () -> Unit,
    deleteAction: (Shift) -> Unit,
) {
    Column() {
        TopAppBar(
            title = {
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
            }
        )
        LazyColumn(Modifier.padding(16.dp)) {
            items(shifts, { shift -> shift.id!! }) { item ->
                val state = rememberDismissState()
                if (state.isDismissed(DismissDirection.StartToEnd) || state.isDismissed(
                        DismissDirection.EndToStart
                    )
                ) {
                    deleteAction(item)
                }
                val color by animateColorAsState(
                    targetValue = when (state.targetValue) {
                        DismissValue.DismissedToStart -> Color.Red
                        DismissValue.DismissedToEnd -> Color.Red
                        else -> Color.LightGray
                    }
                )
                SwipeToDismiss(
                    state = state,
                    background = {},
                ) {
                    CreateShiftListItem(item = item, color = color)
                }
            }
            item {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillParentMaxWidth()
                ) {
                    Text("Monatssume:")
                    Text(
                        "${total().toHours()} h ${
                            (total().toMinutes() % 60).toString().padStart(2, '0')
                        } min"
                    )
                }
            }
        }
    }
}