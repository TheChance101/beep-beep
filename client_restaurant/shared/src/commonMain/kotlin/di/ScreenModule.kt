package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.info.RestaurantInfoScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::RestaurantInfoScreenModel)
}