package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.PreferredFood.PreferredFoodScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::PreferredFoodScreenModel)
}
