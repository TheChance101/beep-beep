package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.usecase.restaurant.*

@Single
data class RestaurantUseCasesContainer(
    val getRestaurants: GetRestaurantsUseCase,
    val getRestaurantDetails: GetRestaurantDetailsUseCase,
    val addRestaurant: CreateRestaurantUseCase,
    val addCategoryToRestaurant: AddCategoryToRestaurantUseCase,
    val updateRestaurant: UpdateRestaurantUseCase,
    val deleteRestaurant: DeleteRestaurantUseCase,
    val deleteCategoriesInRestaurant: DeleteCategoriesInRestaurantUseCase
)



