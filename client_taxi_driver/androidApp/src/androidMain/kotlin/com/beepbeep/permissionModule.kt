package com.beepbeep

import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val permissionModule = module {
    single { PermissionsController(applicationContext = androidContext().applicationContext) }
}

