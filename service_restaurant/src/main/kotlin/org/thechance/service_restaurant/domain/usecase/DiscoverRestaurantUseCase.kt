package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.Category
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.entity.Restaurant
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.InvalidParameterException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.ResourceNotFoundException
import org.thechance.service_restaurant.domain.usecase.validation.Validation

interface IDiscoverRestaurantUseCase {
    suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant>
    suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getMealsByCuisine(cuisineId: String): List<Meal>
    suspend fun getMealDetails(mealId: String): MealDetails
    suspend fun getCategories(page: Int, limit: Int): List<Category>
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>
}

@Single
class DiscoverRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: Validation
) : IDiscoverRestaurantUseCase {
    override suspend fun getRestaurants(page: Int, limit: Int): List<Restaurant> {
        basicValidation.validatePagination(page,limit)
        return restaurantGateway.getRestaurants(page, limit)
    }

    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        basicValidation.validatePagination(page,limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun getCategoriesInRestaurant(restaurantId: String): List<Category> {
        checkIfRestaurantIsExist(restaurantId)
        return optionsGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        checkIfCategoryIsExist(categoryId)
        return optionsGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getRestaurant(restaurantId) ?: throw ResourceNotFoundException(
            NOT_FOUND
        )
    }

    override suspend fun getMealsByCuisine(cuisineId: String): List<Meal> {
        checkIfCuisineIsExist(cuisineId)
        return optionsGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        if (!basicValidation.isValidId(mealId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        return restaurantGateway.getMealById(mealId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!basicValidation.isValidId(categoryId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (optionsGateway.getCategory(categoryId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    private suspend fun checkIfRestaurantIsExist(restaurantId: String) {
        if (!basicValidation.isValidId(restaurantId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (restaurantGateway.getRestaurant(restaurantId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

    private suspend fun checkIfCuisineIsExist(cuisineId: String) {
        if (!basicValidation.isValidId(cuisineId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (optionsGateway.getCuisineById(cuisineId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }
}