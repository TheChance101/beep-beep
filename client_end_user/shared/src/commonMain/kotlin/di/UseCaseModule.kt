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
import domain.usecase.IMangeLanguageUseCase
import domain.usecase.IMangePreferredFoodUseCase
import domain.usecase.InProgressTrackerUseCase
import domain.usecase.ManageAuthenticationUseCase
import domain.usecase.ManageUserUseCase
import domain.usecase.validation.IValidationUseCase
import domain.usecase.validation.ValidationUseCaseUseCase
import domain.usecase.MangeLanguageUseCase
import domain.usecase.MangePreferredFoodUseCase

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
    singleOf(::MangePreferredFoodUseCase) { bind<IMangePreferredFoodUseCase>() }
    singleOf(::ValidationUseCaseUseCase) { bind<IValidationUseCase>() }
    singleOf(::MangeLanguageUseCase) { bind<IMangeLanguageUseCase>() }
}
