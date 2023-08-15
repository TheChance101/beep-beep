package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.*


interface IMealValidationUseCase {

    fun validateAddMeal(meal: MealDetails)

    fun validateUpdateMeal(meal: MealDetails)
}

class MealValidationUseCase(
    private val basicValidation: Validation
) : IMealValidationUseCase{

    override fun validateAddMeal(meal: MealDetails) {
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

    override fun validateUpdateMeal(meal: MealDetails) {
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
            }
            meal.cuisines.forEach {
                if (!basicValidation.isValidId(it.id)) {
                    validationErrors.add(INVALID_ONE_OR_MORE_IDS)
                    return@forEach
                }
            }
        }


        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }
}