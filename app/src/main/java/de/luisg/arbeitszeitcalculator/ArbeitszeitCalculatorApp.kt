package de.luisg.arbeitszeitcalculator

import android.app.Application
import de.luisg.arbeitszeitcalculator.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ArbeitszeitCalculatorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ArbeitszeitCalculatorApp)
            modules(appModule)
        }
    }
}