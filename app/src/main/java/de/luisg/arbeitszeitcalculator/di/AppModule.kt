package de.luisg.arbeitszeitcalculator.di

import androidx.room.Room
import de.luisg.arbeitszeitcalculator.data.data_source.ShiftDatabase
import de.luisg.arbeitszeitcalculator.domain.repository.RoomShiftRepository
import de.luisg.arbeitszeitcalculator.domain.repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.ui.createShiftScreen.CreateShiftViewModel
import de.luisg.arbeitszeitcalculator.ui.icalImportScreen.IcalImporterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<ShiftDatabase> {
        Room.databaseBuilder(
            get(),
            ShiftDatabase::class.java, "database-name"
        ).build()
    }
    single<ShiftRepository> {
        RoomShiftRepository(get())
    }
    single<ShiftUseCases> {
        ShiftUseCases(get(), get())
    }
    single<LoanUseCases> {
        LoanUseCases()
    }

    viewModel<IcalImporterViewModel> {
        IcalImporterViewModel()
    }
    viewModel<CreateShiftViewModel> {
        CreateShiftViewModel()
    }
}