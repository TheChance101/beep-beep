package di

import domain.usecase.GetCuisinesUseCase
import domain.usecase.GetFavoriteRestaurantsUseCase
import domain.usecase.GetNewOffersUserCase
import domain.usecase.IGetCuisinesUseCase
import domain.usecase.IGetFavoriteRestaurantsUseCase
import domain.usecase.IGetNewOffersUserCase
import domain.usecase.IInProgressTrackerUseCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.IManageUserUseCase
import domain.usecase.InProgressTrackerUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.ManageUserUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageAuthenticationUseCase) { bind<IManageAuthenticationUseCase>() }
    singleOf(::GetFavoriteRestaurantsUseCase) { bind<IGetFavoriteRestaurantsUseCase>() }
    singleOf(::GetCuisinesUseCase) { bind<IGetCuisinesUseCase>() }
    singleOf(::GetNewOffersUserCase) { bind<IGetNewOffersUserCase>() }
    singleOf(::ManageUserUseCase) { bind<IManageUserUseCase>() }
    singleOf(::InProgressTrackerUseCase) { bind<IInProgressTrackerUseCase>() }
}
