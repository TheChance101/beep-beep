package di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.app.AppScreenModel
import presentation.auth.login.LoginScreenModel
import presentation.auth.signup.registration.RegistrationScreenModel
import presentation.auth.signup.registrationSubmit.RegistrationSubmitScreenModel
import presentation.cuisines.CuisinesScreenModel
import presentation.home.HomeScreenModel
import presentation.notification.NotificationScreenModel
import presentation.orderHistory.OrderHistoryScreenModel
import presentation.pickLanguage.PickLanguageScreenModel
import presentation.preferredFood.PreferredFoodScreenModel
import presentation.preferredMeal.PreferredScreenModel
import presentation.preferredRide.PreferredRideScreenModel
import presentation.cart.CartScreenModel
import presentation.profile.ProfileScreenModel

val screenModelsModule = module {
    factoryOf(::LoginScreenModel)
    factoryOf(::HomeScreenModel)
    factoryOf(::OrderHistoryScreenModel)
    factoryOf(::PreferredFoodScreenModel)
    factoryOf(::RegistrationScreenModel)
    factoryOf(::RegistrationSubmitScreenModel)
    factoryOf(::PreferredScreenModel)
    factoryOf(::ProfileScreenModel)
    factoryOf(::PickLanguageScreenModel)
    factoryOf(::PreferredRideScreenModel)
    factoryOf(::NotificationScreenModel)
    factoryOf(::CartScreenModel)
    factoryOf(::CuisinesScreenModel)
    factory { AppScreenModel(get()) }
}
