package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.utils.*

interface IDiscoverRestaurantUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealsByCuisine(cuisineId: String): List<Meal>
    suspend fun getMealDetails(mealId: String): MealDetails
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
}

class DiscoverRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
) : IDiscoverRestaurantUseCase {
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        validatePagination(page,limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        validatePagination(page,limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        checkIfCategoryIsExist(categoryId)
        return optionsGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getRestaurant(restaurantId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getMealsByCuisine(cuisineId: String): List<Meal> {
        checkIfCuisineIsExist(cuisineId)
        return optionsGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        if (!isValidId(mealId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        return restaurantGateway.getMealById(mealId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!isValidId(categoryId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (optionsGateway.getCategory(categoryId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!isValidId(restaurantId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (!isValidId(cuisineId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (optionsGateway.getCuisineById(cuisineId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }
}