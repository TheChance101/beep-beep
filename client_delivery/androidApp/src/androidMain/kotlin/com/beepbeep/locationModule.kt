package com.beepbeep

import data.gateway.local.LocationGateway
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import domain.gateway.local.ILocationGateway

val locationDataSourceModule = module {
    single<ILocationGateway> { LocationGateway(get()) }
}

val locationTrackerModule = module {
    single {
        LocationTracker(PermissionsController(applicationContext = androidContext().applicationContext))
    }
}