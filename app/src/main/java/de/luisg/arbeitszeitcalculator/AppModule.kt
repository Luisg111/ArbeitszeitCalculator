package de.luisg.arbeitszeitcalculator

import androidx.room.Room
import de.luisg.arbeitszeitcalculator.shift.data.data_source.ShiftDatabase
import de.luisg.arbeitszeitcalculator.shift.domain.repository.RoomShiftRepository
import de.luisg.arbeitszeitcalculator.shift.domain.repository.ShiftRepository
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.use_cases.LoanUseCases
import de.luisg.arbeitszeitcalculator.shift.domain.useCase.use_cases.ShiftUseCases
import de.luisg.arbeitszeitcalculator.shift.ui.createShiftScreen.CreateShiftViewModel
import de.luisg.arbeitszeitcalculator.shift.ui.icalImportScreen.IcalImporterViewModel
import de.luisg.arbeitszeitcalculator.shift.ui.shiftListScreen.ShiftListViewModel
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
        RoomShiftRepository(get<ShiftDatabase>())
    }
    single<ShiftUseCases> {
        ShiftUseCases(get<ShiftRepository>(), get())
    }
    single<LoanUseCases> {
        LoanUseCases()
    }

    viewModel<IcalImporterViewModel> {
        IcalImporterViewModel()
    }
    viewModel<CreateShiftViewModel> { (shiftId: Int?) ->
        CreateShiftViewModel(id = shiftId)
    }
    viewModel<ShiftListViewModel> {
        ShiftListViewModel()
    }
}