package di

import org.koin.dsl.module
import presentation.main.BpPermissionController
import presentation.main.MainScreenModel

val ScreenModelModule = module {
    factory { MainScreenModel(get()) }
}

val bpPermissionModule = module {
    single { BpPermissionController(get()) }
}

