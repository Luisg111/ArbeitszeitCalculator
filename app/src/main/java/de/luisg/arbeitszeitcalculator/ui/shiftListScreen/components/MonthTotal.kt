package de.luisg.arbeitszeitcalculator.ui.shiftListScreen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.luisg.arbeitszeitcalculator.R
import de.luisg.arbeitszeitcalculator.data.model.Shift
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import org.koin.compose.koinInject

@Composable
fun MonthTotal(
    items: List<Shift>
) {
    val shiftUseCases: ShiftUseCases = koinInject()
    val loanUseCases: LoanUseCases = koinInject()

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