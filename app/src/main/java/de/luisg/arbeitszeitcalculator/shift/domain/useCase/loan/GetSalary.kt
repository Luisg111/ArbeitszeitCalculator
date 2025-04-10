package de.luisg.arbeitszeitcalculator.shift.domain.useCase.loan

class GetSalary(private val salary: () -> Double) {
    operator fun invoke(): Double {
        return salary()
    }
}