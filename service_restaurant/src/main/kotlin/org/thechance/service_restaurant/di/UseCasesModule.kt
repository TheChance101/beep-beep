package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.domain.usecase.*
import org.thechance.service_restaurant.domain.usecase.validation.CategoryValidationUseCase
import org.thechance.service_restaurant.domain.usecase.validation.ICategoryValidationUseCase
import org.thechance.service_restaurant.domain.usecase.validation.IRestaurantValidationUseCase
import org.thechance.service_restaurant.domain.usecase.validation.RestaurantValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation

val UseCasesModule = module {
    single<IValidation> { Validation() }
    single<ICategoryValidationUseCase> { CategoryValidationUseCase(get()) }
    single<IRestaurantValidationUseCase> { RestaurantValidationUseCase(get()) }
    single<IControlRestaurantsUseCase> { ControlRestaurantsUseCase(get(), get(),get()) }
    single<IDiscoverRestaurantUseCase> { DiscoverRestaurantUseCase(get(), get(), get()) }
    single<IManageCategoryUseCase> { ManageCategoryUseCase(get(), get(),get()) }
    single<IManageCuisineUseCase> { ManageCuisineUseCase(get(), get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get(), get(), get(),get()) }
    single<IManageRestaurantDetailsUseCase> { ManageRestaurantDetailsUseCase(get(), get(), get(),get()) }
}