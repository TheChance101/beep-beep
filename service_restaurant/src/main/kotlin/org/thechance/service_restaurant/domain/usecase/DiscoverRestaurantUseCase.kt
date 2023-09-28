package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IDiscoverRestaurantUseCase {
    suspend fun getRestaurants(restaurantOptions: RestaurantOptions): List<Restaurant>
    suspend fun getRestaurantsByIds(restaurantIds: List<String>): List<Restaurant>
    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
    suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant>


    suspend fun getMealsByCuisine(cuisineId: String): List<Meal>
    suspend fun getMealsByRestaurantId(restaurantId: String, page: Int, limit: Int): List<Meal>
    suspend fun getMealDetails(mealId: String): MealDetails
    suspend fun getCategories(page: Int, limit: Int): List<Category>
}

class DiscoverRestaurantUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation
) : IDiscoverRestaurantUseCase {
    override suspend fun getRestaurants(restaurantOptions: RestaurantOptions): List<Restaurant> {
        basicValidation.validatePagination(restaurantOptions.page, restaurantOptions.limit)
        restaurantOptions.priceLevel?.let { basicValidation.isValidatePriceLevel(it) }
        restaurantOptions.rating?.let { basicValidation.isValidRate(it) }
        return restaurantGateway.getRestaurants(restaurantOptions)
    }

    override suspend fun getRestaurantsByIds(restaurantIds: List<String>): List<Restaurant> {
        return restaurantGateway.getRestaurants(restaurantIds)
    }


    override suspend fun getCategories(page: Int, limit: Int): List<Category> {
        basicValidation.validatePagination(page, limit)
        return optionsGateway.getCategories(page, limit)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        checkIfCategoryIsExist(categoryId)
        return optionsGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getRestaurant(restaurantId) ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun getMealsByCuisine(cuisineId: String): List<Meal> {
        checkIfCuisineIsExist(cuisineId)
        return optionsGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealsByRestaurantId(restaurantId: String, page: Int, limit: Int): List<Meal> {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getMealsByRestaurantId(restaurantId, page, limit)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        if (!basicValidation.isValidId(mealId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantGateway.getMealById(mealId) ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!basicValidation.isValidId(categoryId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        if (optionsGateway.getCategory(categoryId) == null) {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
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