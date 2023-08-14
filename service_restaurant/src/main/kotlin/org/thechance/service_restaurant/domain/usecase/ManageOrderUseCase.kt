package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.Validation
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND

interface IManageOrderUseCase {
    suspend fun getOrderById(orderId: String): Order
    suspend fun updateOrderState(orderId: String, state: OrderStatus): Order
}

class ManageOrderUseCase(
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: Validation,
) : IManageOrderUseCase {
    override suspend fun getOrderById(orderId: String): Order {
        require(basicValidation.isValidId(orderId)) { throw MultiErrorException(listOf(NOT_FOUND)) }
        return optionsGateway.getOrderById(orderId)!!
    }


    override suspend fun updateOrderState(orderId: String, state: OrderStatus): Order {

    }

}