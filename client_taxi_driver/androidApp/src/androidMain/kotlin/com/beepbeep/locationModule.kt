package com.beepbeep

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import data.BpLocationDataSource
import domain.dataSource.IBpLocationDataSource

val locationDataSourceModule = module {
    single<IBpLocationDataSource> { BpLocationDataSource(get()) }
}

val locationTrackerModule = module {
    single {
        LocationTracker(PermissionsController(applicationContext = androidContext().applicationContext))
    }
}
