package com.beepbeep

import android.app.Application
import di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App.applicationContext)
            modules(
                appModule(),
                locationTrackerModule,
                locationServiceModule,
                locationDataSourceModule
            )
        }
    }
}
