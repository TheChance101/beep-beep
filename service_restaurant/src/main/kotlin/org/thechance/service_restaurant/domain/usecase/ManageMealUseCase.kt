package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cuisine
import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_CUISINE_LIMIT
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_PRICE
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_UPDATE_PARAMETER
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
    private val basicValidation: IValidation
) : IManageMealUseCase {
    override suspend fun getCuisines(): List<Cuisine> {
        return optionsGateway.getCuisines()
    }

    override suspend fun addMealToRestaurant(meal: MealDetails): Meal {
        validateAddMeal(meal)
        restaurantGateway.getRestaurant(meal.restaurantId) ?: throw MultiErrorException(
            listOf(
                NOT_FOUND
            )
        )
        val cuisineIds = meal.cuisines.map { it.id }
        if (!optionsGateway.areCuisinesExist(cuisineIds)) throw MultiErrorException(listOf(NOT_FOUND))
        restaurantGateway.addCuisineToRestaurant(meal.restaurantId, cuisineIds)
        return restaurantGateway.addMeal(meal)
    }

    override suspend fun updateMealToRestaurant(meal: MealDetails): Meal {
        validateUpdateMeal(meal)
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
            restaurantGateway.getCuisinesNotInRestaurant(meal.restaurantId, cuisineIds!!)
        return restaurantGateway.deleteCuisinesInRestaurant(
            restaurantId = meal.restaurantId,
            cuisinesNeedToDelete
        )
    }

    private fun validateAddMeal(meal: MealDetails) {
        val validationErrors = mutableListOf<Int>()

        if (!(basicValidation.isValidName(meal.name))) {
            validationErrors.add(INVALID_NAME)
        }
        if (!basicValidation.isValidId(meal.restaurantId)) {
            validationErrors.add(INVALID_ID)
        }

        if (meal.description.isEmpty() || !basicValidation.isValidDescription(meal.description)) {
            validationErrors.add(INVALID_DESCRIPTION)
        }

        if (!basicValidation.isValidPrice(meal.price)) {
            validationErrors.add(INVALID_PRICE)
        }

        if (meal.cuisines.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }

        meal.cuisines.forEach {
            if (!basicValidation.isValidId(it.id)) {
                validationErrors.add(INVALID_ONE_OR_MORE_IDS)
                return@forEach
            }
        }


        if (meal.cuisines.size !in 1..Validation.MAX_CUISINE) {
            validationErrors.add(INVALID_CUISINE_LIMIT)
        }

        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    private fun validateUpdateMeal(meal: MealDetails) {
        val validationErrors = mutableListOf<Int>()

        if (meal.id.isEmpty() || meal.restaurantId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }

        if (!basicValidation.isValidId(meal.id) || !basicValidation.isValidId(meal.restaurantId)) {
            validationErrors.add(INVALID_ID)
        }

        if (meal.name.isEmpty() && meal.cuisines.isEmpty() && meal.price == Validation.NULL_DOUBLE && meal.description.isEmpty()) {
            validationErrors.add(INVALID_UPDATE_PARAMETER)
        } else {
            if (meal.name.isNotEmpty() && !basicValidation.isValidName(meal.name)) {
                validationErrors.add(INVALID_NAME)
            }

            if (meal.description.isNotEmpty() && !basicValidation.isValidDescription(meal.description)) {
                validationErrors.add(INVALID_DESCRIPTION)
            }

            if (meal.price != Validation.NULL_DOUBLE && !basicValidation.isValidPrice(meal.price)) {
                validationErrors.add(INVALID_PRICE)
            }

            if (meal.cuisines.isNotEmpty() && meal.cuisines.size !in 1..Validation.MAX_CUISINE) {
                validationErrors.add(INVALID_CUISINE_LIMIT)
                meal.cuisines.forEach {
                    if (!basicValidation.isValidId(it.id)) {
                        validationErrors.add(INVALID_ONE_OR_MORE_IDS)
                        return@forEach
                    }
                }
            }
        }


        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

}