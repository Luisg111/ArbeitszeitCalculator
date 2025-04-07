package de.luisg.arbeitszeitcalculator.ui.shiftList.ShiftList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases

@Composable
fun MonthTotal(
    shiftUseCases: ShiftUseCases,
    loanUseCases: LoanUseCases,
    items: List<Shift>
) {
    var itemsVisible by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    itemsVisible = !itemsVisible
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Monatssume ausgeben
                Text(stringResource(R.string.ShiftListMonthTotalLabel))
                Text(
                    shiftUseCases.displayShiftDuration(items)
                )
            }
        }
        if (itemsVisible) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Monatssume ausgeben
                Text("Monatslohn")
                Text(
                    """${
                        (loanUseCases.getLoan() * (shiftUseCases.getShiftDuration(items)
                            .toMinutes() / 60.0))
                    } €"""
                )
            }
        }
    }
}