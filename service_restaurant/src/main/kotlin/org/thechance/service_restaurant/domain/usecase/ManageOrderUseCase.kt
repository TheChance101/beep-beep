package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException

interface IManageOrderUseCase {
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>
    suspend fun getOrderById(orderId: String): Order
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Order

    suspend fun getOrdersHistory(restaurantId: String,page: Int, limit: Int): List<Order>
}

class ManageOrderUseCase(
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation,
    private val orderValidationUseCase: IOrderValidationUseCase
) : IManageOrderUseCase {
    override suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order> {
       return optionsGateway.getOrdersByRestaurantId(restaurantId)
    }

    override suspend fun getOrderById(orderId: String): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return optionsGateway.getOrderById(orderId)!!
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Order {
        orderValidationUseCase.validateUpdateOrder(orderId, status)
        return optionsGateway.updateOrderStatus(orderId, status)!!
    }

    override suspend fun getOrdersHistory(restaurantId: String,page: Int, limit: Int): List<Order> {
        return optionsGateway.getOrdersHistory(restaurantId,page, limit)
    }

}