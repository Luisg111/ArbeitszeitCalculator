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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.luisg.arbeitszeitcalculator.R


@Composable
fun MonthTotal(
    itemsVisible: Boolean,
    onItemsVisibilityToggled: (isVisible: Boolean?) -> Unit,
    shiftDurationString: String,
    salaryString: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .clickable {
                    onItemsVisibilityToggled(null)
                }
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Monatssume ausgeben
                Text(stringResource(R.string.ShiftListMonthTotalLabel))
                Text(
                    shiftDurationString
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
                Text(salaryString)
            }
        }
    }
}