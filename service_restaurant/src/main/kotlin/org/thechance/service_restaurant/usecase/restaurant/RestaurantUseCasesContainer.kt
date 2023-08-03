package org.thechance.service_restaurant.usecase.restaurant

import org.koin.core.annotation.Single

@Single
data class RestaurantUseCasesContainer(
    val getRestaurants: GetRestaurantsUseCase,
    val getRestaurantDetails: GetRestaurantDetailsUseCase,
    val getCategoriesInRestaurant: GetCategoriesInRestaurantUseCase,
    val getMealsInRestaurant: GetMealsInRestaurantUseCase,

    val addRestaurant: CreateRestaurantUseCase,
    val addCategoryToRestaurant: AddCategoryToRestaurantUseCase,
    val addMealsToRestaurant:AddMealsToRestaurantUseCase,

    val updateRestaurant: UpdateRestaurantUseCase,
    val deleteRestaurant: DeleteRestaurantUseCase,
    val deleteCategoriesInRestaurant: DeleteCategoriesInRestaurantUseCase
)



