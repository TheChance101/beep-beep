package di

import domain.usecase.GetOrderHistoryUseCase
import domain.usecase.IGetOrderHistoryUseCase
import domain.usecase.IInProgressTrackerUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageChatUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IManageNotificationsUseCase
import domain.usecase.IManageOffersUseCase
import domain.usecase.IManageUserUseCase
import domain.usecase.ManageUserUseCase
import domain.usecase.IMangeRestaurantUseCase
import domain.usecase.IMangeUserPreferenceUseCase
import domain.usecase.ISearchUseCase
import domain.usecase.SearchUseCase
import domain.usecase.InProgressTrackerUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.ManageCartUseCase
import domain.usecase.ManageChatUseCase
import domain.usecase.ManageFavouriteUseCase
import domain.usecase.ManageNotificationsUseCase
import domain.usecase.ManageOffersUseCase
import domain.usecase.MangeRestaurantUseCase
import domain.usecase.MangeUserPreferenceUseCase
import domain.usecase.validation.IValidationUseCase
import domain.usecase.validation.ValidationUseCaseUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageAuthenticationUseCase) { bind<IManageAuthenticationUseCase>() }
    singleOf(::ManageOffersUseCase) { bind<IManageOffersUseCase>() }
    singleOf(::ManageUserUseCase) { bind<IManageUserUseCase>() }
    singleOf(::InProgressTrackerUseCase) { bind<IInProgressTrackerUseCase>() }
    singleOf(::MangeUserPreferenceUseCase) { bind<IMangeUserPreferenceUseCase>() }
    singleOf(::ValidationUseCaseUseCase) { bind<IValidationUseCase>() }
    singleOf(::ManageNotificationsUseCase) { bind<IManageNotificationsUseCase>() }
    singleOf(::GetOrderHistoryUseCase) { bind<IGetOrderHistoryUseCase>() }
    singleOf(::MangeRestaurantUseCase) { bind<IMangeRestaurantUseCase>() }
    singleOf(::ManageCartUseCase) { bind<IManageCartUseCase>() }
    singleOf(::ManageChatUseCase) { bind<IManageChatUseCase>() }
    singleOf(::ManageFavouriteUseCase) { bind<IManageFavouriteUseCase>() }
    singleOf(::SearchUseCase) { bind<ISearchUseCase>() }
}
