package de.luisg.arbeitszeitcalculator.domain.useCase.loan

class GetLoan(private val getLoan: () -> Double) {
    operator fun invoke(): Double {
        return getLoan()
    }
}