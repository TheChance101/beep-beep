package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.map.MapScreenModel
import presentation.app.AppScreenModel

val ScreenModelModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::AppScreenModel)
    factory { MapScreenModel(get(), get()) }

}
