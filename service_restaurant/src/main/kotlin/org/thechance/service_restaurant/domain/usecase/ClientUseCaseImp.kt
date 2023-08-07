package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.CategoryGateway
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.NOT_FOUND
import org.thechance.service_restaurant.utils.ResourceNotFoundException

@Single
class ClientUseCaseImp(
    private val restaurantGateway: RestaurantGateway,
    private val categoryGateway: CategoryGateway,
    private val cuisineGateway: CuisineGateway,
    private val mealGateway: MealGateway
) : ClientUseCase {
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        return categoryGateway.getCategories(page, limit)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        return categoryGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        return restaurantGateway.getRestaurant(restaurantId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getMealsInCuisines(cuisineId: String): List<Meal> {
        return cuisineGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        return mealGateway.getMealById(mealId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }
}