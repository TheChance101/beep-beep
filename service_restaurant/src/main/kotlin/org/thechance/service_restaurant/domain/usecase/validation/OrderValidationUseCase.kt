package org.thechance.service_restaurant.domain.usecase.validation

import org.koin.core.component.KoinComponent
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_STATUS
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

interface IOrderValidationUseCase {
    fun validateUpdateOrder(orderId: String, status: OrderStatus)
}

class OrderValidationUseCase(
    private val basicValidation: IValidation
) : IOrderValidationUseCase, KoinComponent {

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
}


