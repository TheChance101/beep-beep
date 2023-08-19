package di

import org.koin.dsl.module
import presentation.screens.home.HomeScreenModel
import presentation.screens.login.LoginScreenModel
import presentation.screens.signup.SignUpScreenModel

val screenModelsModule = module {
    factory { HomeScreenModel() }
    factory { LoginScreenModel(get()) }
    factory { SignUpScreenModel(get()) }
}