package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.login.LoginScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.pickLanguage.PickLanguageScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::PickLanguageScreenModel)
}
