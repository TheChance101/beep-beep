package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.auth.login.LoginScreenModel
import presentation.auth.signup.registration.RegistrationScreenModel
import presentation.auth.signup.registrationSubmit.RegistrationSubmitScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::RegistrationScreenModel)
    factoryOf(::RegistrationSubmitScreenModel)
}
