package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.preferredMeal.PreferredScreenModel
import presentation.preferredRide.PreferredRideScreenModel
import presentation.pickLanguage.PickLanguageScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::PreferredScreenModel)
    factoryOf(::PickLanguageScreenModel)
    factoryOf(::PreferredRideScreenModel)
}
