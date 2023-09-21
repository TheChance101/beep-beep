package com.beepbeep

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import data.gateway.remote.LocationDataSource
import domain.gateway.ILocationDataSource

val locationDataSourceModule = module {
    single<ILocationDataSource> { LocationDataSource(get()) }
}

val locationTrackerModule = module {
    single {
        LocationTracker(PermissionsController(applicationContext = androidContext().applicationContext))
    }
}