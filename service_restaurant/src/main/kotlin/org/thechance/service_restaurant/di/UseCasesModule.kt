package org.thechance.service_restaurant.di

import org.koin.dsl.module
import org.thechance.service_restaurant.domain.usecase.ControlRestaurantsUseCase
import org.thechance.service_restaurant.domain.usecase.DiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IControlRestaurantsUseCase
import org.thechance.service_restaurant.domain.usecase.IDiscoverRestaurantUseCase
import org.thechance.service_restaurant.domain.usecase.IManageCategoryUseCase
import org.thechance.service_restaurant.domain.usecase.IManageCuisineUseCase
import org.thechance.service_restaurant.domain.usecase.IManageMealUseCase
import org.thechance.service_restaurant.domain.usecase.IManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.domain.usecase.ManageCategoryUseCase
import org.thechance.service_restaurant.domain.usecase.ManageCuisineUseCase
import org.thechance.service_restaurant.domain.usecase.ManageMealUseCase
import org.thechance.service_restaurant.domain.usecase.ManageRestaurantDetailsUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation

val UseCasesModule = module {
    single<IValidation> { Validation() }
    single<IControlRestaurantsUseCase> { ControlRestaurantsUseCase(get(), get()) }
    single<IDiscoverRestaurantUseCase> { DiscoverRestaurantUseCase(get(), get(), get()) }
    single<IManageCategoryUseCase> { ManageCategoryUseCase(get(), get()) }
    single<IManageCuisineUseCase> { ManageCuisineUseCase(get(), get()) }
    single<IManageMealUseCase> { ManageMealUseCase(get(), get(), get()) }
    single<IManageRestaurantDetailsUseCase> { ManageRestaurantDetailsUseCase(get(), get(), get()) }
}