package de.luisg.arbeitszeitcalculator.viewmodel.use_case.loan

class SetLoan(private val setLoan: (Double) -> Unit) {
    operator fun invoke(loan: Double) {
        setLoan(loan)
    }
}