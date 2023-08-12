package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.MealValidation
import org.thechance.service_restaurant.domain.usecase.validation.Validation
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.MultiErrorException
import org.thechance.service_restaurant.domain.utils.NOT_FOUND

interface IManageMealUseCase {
    suspend fun getCuisines(page: Int, limit: Int): List<Cuisine>
    suspend fun addMealToRestaurant(meal: MealDetails): Boolean
    suspend fun updateMealToRestaurant(meal: MealDetails): Boolean
    suspend fun deleteMealFromRestaurant(mealId: String): Boolean
}

class ManageMealUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val mealValidation: MealValidation,
    private val basicValidation: Validation
) : IManageMealUseCase {
    override suspend fun getCuisines(page: Int, limit: Int): List<Cuisine> {
        basicValidation.validatePagination(page, limit)
        return optionsGateway.getCuisines(page, limit)
    }

    override suspend fun addMealToRestaurant(meal: MealDetails): Boolean {
        mealValidation.validateAddMeal(meal)
        restaurantGateway.getRestaurant(meal.restaurantId) ?: throw MultiErrorException(listOf(NOT_FOUND))
        val cuisineIds = meal.cuisines.map { it.id }
        if (!optionsGateway.areCuisinesExist(cuisineIds)) throw MultiErrorException(listOf(NOT_FOUND))
        restaurantGateway.addCuisineToRestaurant(meal.restaurantId, cuisineIds)
        return restaurantGateway.addMeal(meal)
    }

    override suspend fun updateMealToRestaurant(meal: MealDetails): Boolean {
        mealValidation.validateUpdateMeal(meal)
        return restaurantGateway.updateMeal(meal)
    }

    override suspend fun deleteMealFromRestaurant(mealId: String): Boolean {
        if (!basicValidation.isValidId(mealId)) throw MultiErrorException(listOf(INVALID_ID))
        val meal = restaurantGateway.getMealById(mealId) ?: throw MultiErrorException(listOf(NOT_FOUND))
        val cuisineIds = meal.cuisines.map { it.id }
        restaurantGateway.deleteMealById(mealId)
        val cuisinesNeedToDelete = restaurantGateway.getCuisinesNotInRestaurant(meal.restaurantId, cuisineIds!!)
        return restaurantGateway.deleteCuisinesInRestaurant(restaurantId = meal.restaurantId, cuisinesNeedToDelete)
    }
}