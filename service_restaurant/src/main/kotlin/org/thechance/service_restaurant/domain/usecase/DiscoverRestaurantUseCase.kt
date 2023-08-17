package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IDiscoverRestaurantUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealsByCuisine(cuisineId: String): List<Meal>
    suspend fun getMealDetails(mealId: String): MealDetails
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
}

class DiscoverRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation
) : IDiscoverRestaurantUseCase {
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        basicValidation.validatePagination(page,limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        basicValidation.validatePagination(page,limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        checkIfCategoryIsExist(categoryId)
        return optionsGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getRestaurant(restaurantId) ?:
        throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun getMealsByCuisine(cuisineId: String): List<Meal> {
        checkIfCuisineIsExist(cuisineId)
        return optionsGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        if (!basicValidation.isValidId(mealId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantGateway.getMealById(mealId) ?:
        throw MultiErrorException(listOf(NOT_FOUND))
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!basicValidation.isValidId(categoryId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (optionsGateway.getCategory(categoryId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))        }
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (!basicValidation.isValidId(cuisineId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (optionsGateway.getCuisineById(cuisineId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }
}