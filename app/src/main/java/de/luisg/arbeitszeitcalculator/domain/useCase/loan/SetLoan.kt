package de.luisg.arbeitszeitcalculator.domain.useCase.loan

class SetLoan(private val setLoan: (Double) -> Unit) {
    operator fun invoke(loan: Double) {
        setLoan(loan)
    }
}