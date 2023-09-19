package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.PreferredFood.PreferredFoodScreenModel
import presentation.auth.login.LoginScreenModel
import presentation.auth.signup.registration.RegistrationScreenModel
import presentation.auth.signup.registrationSubmit.RegistrationSubmitScreenModel
import presentation.home.HomeScreenModel
import presentation.main.MainScreenModel
import presentation.pickLanguage.PickLanguageScreenModel
import presentation.preferredMeal.PreferredScreenModel
import presentation.preferredRide.PreferredRideScreenModel
import presentation.notification.NotificationScreenModel

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
    factoryOf(::NotificationScreenModel)
}
