package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.entity.MealRequest
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_QUANTITY
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.RESTAURANT_NOT_SAME_IN_CART

interface ICartValidationUseCase {
    fun validateUpdateCart(userId: String, restaurantId: String, mealId: String, quantity: Int)
    fun validateUpdateCart(userId: String, meals: List<MealRequest>)

}

class CartValidationUseCase(private val basicValidation: IValidation) : ICartValidationUseCase {
    override fun validateUpdateCart(userId: String, restaurantId: String, mealId: String, quantity: Int) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(userId) ||
            !basicValidation.isValidId(restaurantId) ||
            !basicValidation.isValidId(mealId)
        ) {
            validationErrors.add(INVALID_ID)
        }

        if (quantity < 0) {
            validationErrors.add(INVALID_QUANTITY)
        }

        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    override fun validateUpdateCart(userId: String, meals: List<MealRequest>) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(userId)) {
            validationErrors.add(INVALID_ID)
        }

        if (meals.isEmpty()) {
            validationErrors.add(INVALID_QUANTITY)
        }

        if (meals.isNotEmpty() && meals.any { it.restaurantId != meals[0].restaurantId }) {
            validationErrors.add(RESTAURANT_NOT_SAME_IN_CART)
        }

        meals.forEach {
            if (!basicValidation.isValidId(it.mealId)) {
                validationErrors.add(INVALID_ID)
            }
            if (it.quantity < 0) {
                validationErrors.add(INVALID_QUANTITY)
            }
        }

        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

}