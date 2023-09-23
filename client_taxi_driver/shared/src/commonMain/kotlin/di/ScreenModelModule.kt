package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.map.MapScreenModel

val ScreenModelModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::MapScreenModel)
}
