package de.luisg.arbeitszeitcalculator.domain.useCase.use_cases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import de.luisg.arbeitszeitcalculator.domain.useCase.loan.GetLoan
import de.luisg.arbeitszeitcalculator.domain.useCase.loan.SetLoan

class LoanUseCases {
    private var loan by mutableStateOf(11.0)
    val setLoan = SetLoan() { loan = it }
    val getLoan = GetLoan() { return@GetLoan loan }
}