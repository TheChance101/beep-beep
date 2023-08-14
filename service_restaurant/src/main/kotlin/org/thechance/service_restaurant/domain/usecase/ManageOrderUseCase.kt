package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.*

interface IManageOrderUseCase {
    suspend fun getOrderById(orderId: String): Order
    suspend fun updateOrderState(orderId: String, state: OrderStatus): Order
}

class ManageOrderUseCase(
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: Validation,
) : IManageOrderUseCase {

    override suspend fun getOrderById(orderId: String): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return optionsGateway.getOrderById(orderId)!!
    }


    override suspend fun updateOrderState(orderId: String, state: OrderStatus): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        } else if (state.statusCode !in 0..4) {
            throw MultiErrorException(listOf(INVALID_STATE))
        } else {
            return optionsGateway.updateOrderState(orderId, state)!!
        }
    }

}