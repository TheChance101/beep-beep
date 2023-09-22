package com.beepbeep

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationTrackerModule = module {
    single {
        LocationTracker(
            PermissionsController(applicationContext = androidContext().applicationContext)
        )
    }
}