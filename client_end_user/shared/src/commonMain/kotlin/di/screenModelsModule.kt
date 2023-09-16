package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.auth.login.LoginScreenModel
import presentation.auth.signup.registration.RegistrationScreenModel
import presentation.auth.signup.registrationSubmit.RegistrationSubmitScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.preferredMeal.PreferredScreenModel
import presentation.preferredRide.PreferredRideScreenModel
import presentation.pickLanguage.PickLanguageScreenModel
import presentation.app.AppScreenModel

import presentation.PreferredFood.PreferredFoodScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::MainScreenModel)
    factoryOf(::PreferredFoodScreenModel)
    factoryOf(::RegistrationScreenModel)
    factoryOf(::RegistrationSubmitScreenModel)
    factoryOf(::PreferredScreenModel)
    factoryOf(::PickLanguageScreenModel)
    factoryOf(::PreferredRideScreenModel)
}
