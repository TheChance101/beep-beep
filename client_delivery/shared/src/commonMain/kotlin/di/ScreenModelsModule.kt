package di

import presentation.app.AppScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.map.MapScreenModel

val screenModelsModule = module {
    factoryOf(::MainScreenModel)
    factoryOf(::LoginScreenModel)
    factoryOf(::AppScreenModel)
    factory { MapScreenModel(get(),get(),get()) }
}