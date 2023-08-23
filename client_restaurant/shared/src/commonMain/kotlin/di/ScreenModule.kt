package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.main.MainScreenModel
import presentation.order.OrderScreenModel
import presentation.order.order_history.OrderHistoryScreenModel

val screenModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::OrderScreenModel)
    factoryOf(::OrderHistoryScreenModel)
}