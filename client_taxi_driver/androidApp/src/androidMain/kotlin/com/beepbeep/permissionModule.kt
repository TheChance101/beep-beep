package com.beepbeep

import dev.icerock.moko.permissions.PermissionsController
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import presentation.main.BpPermissionController


val permissionModule = module {
    single { BpPermissionController(PermissionsController(applicationContext = androidContext().applicationContext)) }
}

