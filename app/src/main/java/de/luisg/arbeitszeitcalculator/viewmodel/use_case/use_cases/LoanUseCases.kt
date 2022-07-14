package de.luisg.arbeitszeitcalculator.viewmodel.use_case.use_cases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.loan.GetLoan
import de.luisg.arbeitszeitcalculator.viewmodel.use_case.loan.SetLoan

class LoanUseCases {
    private var loan by mutableStateOf(11.0)
    val setLoan = SetLoan() { loan = it }
    val getLoan = GetLoan() { return@GetLoan loan }
}