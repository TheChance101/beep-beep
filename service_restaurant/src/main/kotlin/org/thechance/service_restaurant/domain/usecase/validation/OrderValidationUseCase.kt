package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_REQUEST_PARAMETER
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

interface IOrderValidationUseCase {
    fun validateGetOrdersByRestaurantId(restaurantId: String)
    fun validateGetOrderById(orderId: String)
    fun validateUpdateOrder(orderId: String)
    fun validateGetOrdersHistory(restaurantId: String, page: Int, limit: Int)

}

class OrderValidationUseCase(
    private val basicValidation: IValidation
) : IOrderValidationUseCase {
    override fun validateGetOrdersByRestaurantId(restaurantId: String) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidId(restaurantId)) {
            validationErrors.add(INVALID_ID)
        }
        if (restaurantId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    override fun validateGetOrderById(orderId: String) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidId(orderId)) {
            validationErrors.add(INVALID_ID)
        }
        if (orderId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    override fun validateUpdateOrder(orderId: String) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(orderId)) {
            validationErrors.add(INVALID_ID)
        }
        if (orderId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

    override fun validateGetOrdersHistory(restaurantId: String, page: Int, limit: Int) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidId(restaurantId)) {
            validationErrors.add(INVALID_ID)
        }
        if (restaurantId.isEmpty()) {
            validationErrors.add(INVALID_REQUEST_PARAMETER)
        }
        if (validationErrors.isNotEmpty()) {
            throw MultiErrorException(validationErrors)
        }
    }

}


