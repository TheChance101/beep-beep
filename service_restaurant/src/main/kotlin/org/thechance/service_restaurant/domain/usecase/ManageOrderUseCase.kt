package org.thechance.service_restaurant.domain.usecase

import kotlinx.datetime.toJavaLocalDateTime
import org.thechance.service_restaurant.api.utils.isRestaurantOpen
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.currentDateTime
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

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<Int, Int>> // list of maps (dayOfWeek, count) { dayOfWeek 0 - 6 (Sunday - Saturday) }

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

    override suspend fun getOrdersCountByDaysBefore(restaurantId: String, daysBack: Int): List<Map<Int, Int>> {
        basicValidation.isValidId(restaurantId).takeIf { it }?.let {
            val currentDateTime = currentDateTime().toJavaLocalDateTime()
            val currentDayOfYear = currentDateTime.dayOfYear
            val dayOfYearBefore = currentDateTime.minusDays(daysBack.toLong()).dayOfYear
            val daysOfYearRange = dayOfYearBefore..currentDayOfYear
            val groupedOrdersByDayOfYear = optionsGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
                .filter { it.createdAt.dayOfYear in daysOfYearRange }.groupBy { it.createdAt.dayOfYear }

            // convert to list of maps (dayOfWeek, count)
            return daysOfYearRange.map {
                val count = groupedOrdersByDayOfYear[it]?.size ?: 0
                mapOf(currentDateTime.withDayOfYear(it).dayOfWeek.value to count)
            }.reversed()
        } ?: throw MultiErrorException(listOf(INVALID_ID))
    }

    private suspend fun isRestaurantOpened(restaurantId: String): Boolean {
        val restaurant = restaurantGateway.getRestaurant(id = restaurantId)
        return restaurant?.let {
            isRestaurantOpen(openTime = it.openingTime, closeTime = it.closingTime)
        } ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

}