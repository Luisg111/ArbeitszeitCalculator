package de.luisg.arbeitszeitcalculator.ui.show_shifts

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases.ShiftUseCases
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateShiftListItem(
    item: Shift,
    backgroundColor: Color,
    foregroundColor: Color,
    shiftUseCases: ShiftUseCases,
    navController: NavController
) {
    //DismissState für das Löschen von Schichten
    val state = rememberDismissState()

    //Lösche Schicht, wenn DismissState ist Dismissed
    if (state.isDismissed(DismissDirection.StartToEnd) || state.isDismissed(
            DismissDirection.EndToStart
        )
    ) {
        LaunchedEffect(key1 = item) {
            shiftUseCases.deleteShift(item)
        }
    }

    //Passe Farbe an wenn Element geswiped wird
    val color by animateColorAsState(
        targetValue = when (state.targetValue) {
            DismissValue.DismissedToStart -> Color.Red
            DismissValue.DismissedToEnd -> Color.Red
            else -> backgroundColor
        }
    )

    //Swipebarer Eintrag
    SwipeToDismiss(
        state = state,
        background = {},
    ) {
        //Farbige Box im Hintergrund
        Box(
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 6.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
                .clickable {
                    navController.navigate("update/${item.id}")
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column() {
                    //Anfangsdatum als text
                    Text(
                        text =
                        DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                            .format(item.startDateTime.toLocalDate()),
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = foregroundColor
                    )

                    //Start- und Endzeit als Text
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
                //Dauer als Text
                Text(
                    shiftUseCases.displayShiftDuration(item),
                    color = foregroundColor
                )
            }
        }
    }
}