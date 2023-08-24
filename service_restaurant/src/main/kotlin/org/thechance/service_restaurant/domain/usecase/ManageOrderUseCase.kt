package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.api.utils.isRestaurantOpen
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.exceptions.RESTAURANT_CLOSED

interface IManageOrderUseCase {
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>

    suspend fun addOrder(order: Order): Boolean

    suspend fun updateOrderStatus(orderId: String, state: OrderStatus): Order

    suspend fun getOrderById(orderId: String): Order

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order>

    suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order>

}

class ManageOrderUseCase(
    private val optionsGateway: IRestaurantOptionsGateway,
    private val restaurantGateway: IRestaurantGateway,
    private val basicValidation: IValidation,
    private val orderValidationUseCase: IOrderValidationUseCase
) : IManageOrderUseCase {

    override suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order> {
        return optionsGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
    }

    override suspend fun getOrderById(orderId: String): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return optionsGateway.getOrderById(orderId = orderId)!!
    }

    override suspend fun addOrder(order: Order): Boolean {
        return if (isRestaurantOpened(order.restaurantId)) {
            optionsGateway.addOrder(order = order)
        } else {
            throw MultiErrorException(listOf(RESTAURANT_CLOSED))
        }
    }

    override suspend fun updateOrderStatus(orderId: String, state: OrderStatus): Order {
        orderValidationUseCase.validateUpdateOrder(orderId = orderId, status = state)
        return optionsGateway.updateOrderStatus(orderId = orderId, status = state)!!
    }

    override suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order> {
        return optionsGateway.getOrdersHistory(restaurantId = restaurantId, page = page, limit = limit)
    }

    override suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order> {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return optionsGateway.getActiveOrdersByRestaurantId(restaurantId = restaurantId)
    }

    private suspend fun isRestaurantOpened(restaurantId: String): Boolean {
        val restaurant = restaurantGateway.getRestaurant(id = restaurantId)
        return restaurant?.let {
            isRestaurantOpen(openTime = it.openingTime, closeTime = it.closingTime)
        } ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

}