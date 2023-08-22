package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.order.OrderScreen
import presentation.order.OrderScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::OrderScreenModel)
}