package com.beepbeep.di

import com.beepbeep.location.LocationService
import data.gateway.local.LocationGateway
import data.gateway.service.ILocationService
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import domain.gateway.local.ILocationGateway
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val locationDataSourceModule = module {
    single<ILocationGateway> { LocationGateway(get(), get()) }
}

val locationTrackerModule = module {
    single {
        LocationTracker(PermissionsController(applicationContext = androidContext().applicationContext))
    }
}
val locationServiceModule = module {
    single<ILocationService> { LocationService(androidContext().applicationContext) }
}