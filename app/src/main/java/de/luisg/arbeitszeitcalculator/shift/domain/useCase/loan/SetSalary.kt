package de.luisg.arbeitszeitcalculator.shift.domain.useCase.loan

class SetSalary(private val salary: (Double) -> Unit) {
    operator fun invoke(loan: Double) {
        salary(loan)
    }
}