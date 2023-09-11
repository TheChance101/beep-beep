package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.home.HomeScreenModel
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.orders.OrderScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::OrderScreenModel)
}
