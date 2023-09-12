package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.preferredMeal.PreferredScreenModel
import presentation.pickLanguage.PickLanguageScreenModel
import presentation.PreferredFood.PreferredFoodScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::PreferredFoodScreenModel)
    factoryOf(::PreferredScreenModel)
    factoryOf(::PickLanguageScreenModel)
}
