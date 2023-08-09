package org.thechance.service_restaurant.domain.usecase

import org.koin.core.annotation.Single
import org.thechance.service_restaurant.domain.entity.*
import org.thechance.service_restaurant.domain.gateway.CategoryGateway
import org.thechance.service_restaurant.domain.gateway.CuisineGateway
import org.thechance.service_restaurant.domain.gateway.MealGateway
import org.thechance.service_restaurant.domain.gateway.RestaurantGateway
import org.thechance.service_restaurant.utils.*

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
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getCategoriesInRestaurant(restaurantId)
    }

    override suspend fun getRestaurantsInCategory(categoryId: String): List<Restaurant> {
        checkIfCategoryIsExist(categoryId)
        return categoryGateway.getRestaurantsInCategory(categoryId)
    }

    override suspend fun getRestaurantDetails(restaurantId: String): Restaurant {
        checkIfRestaurantIsExist(restaurantId)
        return restaurantGateway.getRestaurant(restaurantId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    override suspend fun getMealsInCuisines(cuisineId: String): List<Meal> {
        checkIfCuisineIsExist(cuisineId)
        return cuisineGateway.getMealsInCuisine(cuisineId)
    }

    override suspend fun getMealDetails(mealId: String): MealDetails {
        if (!isValidId(mealId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        return mealGateway.getMealById(mealId) ?: throw ResourceNotFoundException(NOT_FOUND)
    }

    private suspend fun checkIfCategoryIsExist(categoryId: String) {
        if (!isValidId(categoryId)) {
            throw InvalidParameterException(INVALID_ID)
        }
        if (categoryGateway.getCategory(categoryId) == null) {
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
        if (cuisineGateway.getCuisineById(cuisineId) == null) {
            throw ResourceNotFoundException(NOT_FOUND)
        }
    }

}