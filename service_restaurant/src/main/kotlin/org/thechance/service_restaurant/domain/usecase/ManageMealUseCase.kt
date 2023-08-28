package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.utils.getCurrencyForLocation
import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IMealValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageMealUseCase {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun addMealToRestaurant(meal: MealDetails): Meal
    suspend fun updateMealToRestaurant(meal: MealDetails): Meal
    suspend fun deleteMealFromRestaurant(mealId: String): Boolean
}

class ManageMealUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation,
    private val mealValidation: IMealValidationUseCase
) : IManageMealUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return optionsGateway.getCuisines()
    }

    override suspend fun addMealToRestaurant(meal: MealDetails): Meal {
        mealValidation.validateAddMeal(meal)
        val restaurant =
            restaurantGateway.getRestaurant(meal.restaurantId) ?: throw MultiErrorException(listOf(NOT_FOUND))
        val cuisineIds = meal.cuisines.map { it.id }
        if (!optionsGateway.areCuisinesExist(cuisineIds)) throw MultiErrorException(listOf(NOT_FOUND))
        restaurantGateway.addCuisineToRestaurant(meal.restaurantId, cuisineIds)
        return restaurantGateway.addMeal(meal.copy(currency = restaurant.currency))
    }

    override suspend fun updateMealToRestaurant(meal: MealDetails): Meal {
        mealValidation.validateUpdateMeal(meal)
        return restaurantGateway.updateMeal(meal)
    }

    override suspend fun deleteMealFromRestaurant(mealId: String): Boolean {
        if (!basicValidation.isValidId(mealId)) throw MultiErrorException(listOf(INVALID_ID))
        val meal = restaurantGateway.getMealById(mealId) ?: throw MultiErrorException(
            listOf(
                NOT_FOUND
            )
        )
        val cuisineIds = meal.cuisines.map { it.id }
        restaurantGateway.deleteMealById(mealId)
        val cuisinesNeedToDelete =
            restaurantGateway.getCuisinesNotInRestaurant(meal.restaurantId, cuisineIds)
        return restaurantGateway.deleteCuisinesInRestaurant(
            restaurantId = meal.restaurantId,
            cuisinesNeedToDelete
        )
    }

}