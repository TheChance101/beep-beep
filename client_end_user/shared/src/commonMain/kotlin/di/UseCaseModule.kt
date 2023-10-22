package di

import domain.usecase.ChatUseCase
import domain.usecase.ExploreRestaurantUseCase
import domain.usecase.GetNotificationsUseCase
import domain.usecase.GetOffersUseCase
import domain.usecase.GetTransactionHistoryUseCase
import domain.usecase.GetUserLocationUseCase
import domain.usecase.IChatUseCase
import domain.usecase.IExploreRestaurantUseCase
import domain.usecase.IGetNotificationsUseCase
import domain.usecase.IGetOffersUseCase
import domain.usecase.IGetTransactionHistoryUseCase
import domain.usecase.IGetUserLocationUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageCartUseCase
import domain.usecase.IManageFavouriteUseCase
import domain.usecase.IManageProfileUseCase
import domain.usecase.IManageSettingUseCase
import domain.usecase.ISearchUseCase
import domain.usecase.ITrackOrdersUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.ManageCartUseCase
import domain.usecase.ManageFavouriteUseCase
import domain.usecase.ManageProfileUseCase
import domain.usecase.ManageSettingUseCase
import domain.usecase.SearchUseCase
import domain.usecase.TrackOrdersUseCase
import domain.usecase.validation.IValidationUseCase
import domain.usecase.validation.ValidationUseCaseUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageAuthenticationUseCase) { bind<IManageAuthenticationUseCase>() }
    singleOf(::GetOffersUseCase) { bind<IGetOffersUseCase>() }
    singleOf(::ManageSettingUseCase) { bind<IManageSettingUseCase>() }
    singleOf(::TrackOrdersUseCase) { bind<ITrackOrdersUseCase>() }
    singleOf(::ValidationUseCaseUseCase) { bind<IValidationUseCase>() }
    singleOf(::GetNotificationsUseCase) { bind<IGetNotificationsUseCase>() }
    singleOf(::GetTransactionHistoryUseCase) { bind<IGetTransactionHistoryUseCase>() }
    singleOf(::ExploreRestaurantUseCase) { bind<IExploreRestaurantUseCase>() }
    singleOf(::ManageCartUseCase) { bind<IManageCartUseCase>() }
    singleOf(::ChatUseCase) { bind<IChatUseCase>() }
    singleOf(::ManageFavouriteUseCase) { bind<IManageFavouriteUseCase>() }
    singleOf(::SearchUseCase) { bind<ISearchUseCase>() }
    singleOf(::ManageProfileUseCase) { bind<IManageProfileUseCase>() }
    singleOf(::GetUserLocationUseCase) { bind<IGetUserLocationUseCase>() }
}
