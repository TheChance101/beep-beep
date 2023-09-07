package di

import domain.usecase.IGetFavoriteRestaurantsUseCase
import domain.usecase.IGetCuisinesUseCase
import domain.usecase.GetCuisinesUseCase
import domain.usecase.GetNewOffersUserCase
import domain.usecase.IGetNewOffersUserCase
import domain.usecase.IManageAuthenticationUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.GetFavoriteRestaurantsUseCase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {
    singleOf(::ManageAuthenticationUseCase) { bind<IManageAuthenticationUseCase>() }
    singleOf(::GetFavoriteRestaurantsUseCase) { bind<IGetFavoriteRestaurantsUseCase>() }
    singleOf(::GetCuisinesUseCase) { bind<IGetCuisinesUseCase>() }
    singleOf(::GetNewOffersUserCase) { bind<IGetNewOffersUserCase>() }

}