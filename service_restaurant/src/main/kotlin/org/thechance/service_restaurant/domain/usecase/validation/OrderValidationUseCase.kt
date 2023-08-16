package org.thechance.service_restaurant.domain.usecase.validation

import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_STATUS
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_TIME
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import java.text.SimpleDateFormat
import java.util.*

interface IOrderValidationUseCase {
    fun validateGetOrdersByRestaurantId(restaurantId: String)
    fun validateGetOrderById(orderId: String)
    fun validateUpdateOrder(orderId: String, status: OrderStatus)
    fun validateGetOrdersHistory(restaurantId: String, page: Int, limit: Int)
    fun validateIsRestaurantOpen(openingTime: String, closingTime: String)
    fun isRestaurantOpen(openTime: String, closeTime: String): Boolean

}

class OrderValidationUseCase(
    private val basicValidation: IValidation
) : IOrderValidationUseCase {
    override fun validateGetOrdersByRestaurantId(restaurantId: String) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidId(restaurantId)) {
            validationErrors.add(INVALID_ID)
        }
    }

    override fun validateGetOrderById(orderId: String) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidId(orderId)) {
            validationErrors.add(INVALID_ID)
        }
    }

    override fun validateUpdateOrder(orderId: String, status: OrderStatus) {
        val validationErrors = mutableListOf<Int>()

        if (!basicValidation.isValidId(orderId)) {
            validationErrors.add(INVALID_ID)
        }
        if (status.statusCode !in 0..4) {
            validationErrors.add(INVALID_STATUS)
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
    }

    override fun validateIsRestaurantOpen(openingTime: String, closingTime: String) {
        val validationErrors = mutableListOf<Int>()
        if (!basicValidation.isValidTime(openingTime) || !basicValidation.isValidTime(closingTime)) {
            validationErrors.add(INVALID_TIME)
        }
    }


}


