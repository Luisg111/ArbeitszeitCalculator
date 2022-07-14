package de.luisg.arbeitszeitcalculator.viewmodel.use_case.loan

class GetLoan(private val getLoan: () -> Double) {
    operator fun invoke(): Double {
        return getLoan()
    }
}