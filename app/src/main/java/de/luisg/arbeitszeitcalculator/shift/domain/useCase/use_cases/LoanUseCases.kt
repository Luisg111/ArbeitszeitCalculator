package de.luisg.arbeitszeitcalculator.shift.domain.useCase.use_cases

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.setValue
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.loan.GetSalary
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.loan.SetSalary

class LoanUseCases {
    private var loan by mutableDoubleStateOf(11.0)
    val setLoan = SetSalary { loan = it }
    val getLoan = GetSalary { return@GetSalary loan }
}