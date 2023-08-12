package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.entity.MealDetails
import org.thechance.service_restaurant.domain.utils.INVALID_DESCRIPTION
import org.thechance.service_restaurant.domain.utils.INVALID_ID
import org.thechance.service_restaurant.domain.utils.INVALID_NAME
import org.thechance.service_restaurant.domain.utils.INVALID_ONE_OR_MORE_IDS
import org.thechance.service_restaurant.domain.utils.INVALID_PRICE
import org.thechance.service_restaurant.domain.utils.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.INVALID_UPDATE_PARAMETER
import org.thechance.service_restaurant.domain.utils.MultiErrorException

class MealValidation(
    private val basicValidation: Validation
) {

    fun validateAddMeal(meal: MealDetails) {
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

        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    fun validateUpdateMeal(meal: MealDetails) {
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
            if (!basicValidation.isValidName(meal.name)) {
                validationErrors.add(INVALID_NAME)
            }


            if (!basicValidation.isValidDescription(meal.description)) {
                validationErrors.add(INVALID_DESCRIPTION)
            }

            if (!basicValidation.isValidPrice(meal.price)) {
                validationErrors.add(INVALID_PRICE)
            }

            meal.cuisines.forEach {
                if (!basicValidation.isValidId(it.id)) {
                    validationErrors.add(INVALID_ID)
                    return@forEach
                }
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