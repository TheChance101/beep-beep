package di

import domain.usecase.GetCuisinesUseCase
import domain.usecase.GetOrderHistoryUseCase
import domain.usecase.IGetCuisinesUseCase
import domain.usecase.IGetOrderHistoryUseCase
import domain.usecase.IInProgressTrackerUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageChatUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IManageNotificationsUseCase
import domain.usecase.IManageOffersUseCase
import domain.usecase.IManageUserUseCase
import domain.usecase.IMangeLanguageUseCase
import domain.usecase.IMangePreferredFoodUseCase
import domain.usecase.IMangeRestaurantUseCase
import domain.usecase.InProgressTrackerUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.ManageCartUseCase
import domain.usecase.ManageChatUseCase
import domain.usecase.ManageFavouriteUseCase
import domain.usecase.ManageNotificationsUseCase
import domain.usecase.ManageOffersUseCase
import domain.usecase.ManageUserUseCase
import domain.usecase.MangeLanguageUseCase
import domain.usecase.MangePreferredFoodUseCase
import domain.usecase.MangeRestaurantUseCase
import domain.usecase.validation.IValidationUseCase
import domain.usecase.validation.ValidationUseCaseUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageAuthenticationUseCase) { bind<IManageAuthenticationUseCase>() }
    singleOf(::GetCuisinesUseCase) { bind<IGetCuisinesUseCase>() }
    singleOf(::ManageOffersUseCase) { bind<IManageOffersUseCase>() }
    singleOf(::ManageUserUseCase) { bind<IManageUserUseCase>() }
    singleOf(::InProgressTrackerUseCase) { bind<IInProgressTrackerUseCase>() }
    singleOf(::MangePreferredFoodUseCase) { bind<IMangePreferredFoodUseCase>() }
    singleOf(::ValidationUseCaseUseCase) { bind<IValidationUseCase>() }
    singleOf(::MangeLanguageUseCase) { bind<IMangeLanguageUseCase>() }
    singleOf(::ManageNotificationsUseCase) { bind<IManageNotificationsUseCase>() }
    singleOf(::GetOrderHistoryUseCase) { bind<IGetOrderHistoryUseCase>() }
    singleOf(::MangeRestaurantUseCase) { bind<IMangeRestaurantUseCase>() }
    singleOf(::ManageCartUseCase) { bind<IManageCartUseCase>() }
    singleOf(::ManageChatUseCase) { bind<IManageChatUseCase>() }
    singleOf(::ManageFavouriteUseCase) { bind<IManageFavouriteUseCase>() }
}