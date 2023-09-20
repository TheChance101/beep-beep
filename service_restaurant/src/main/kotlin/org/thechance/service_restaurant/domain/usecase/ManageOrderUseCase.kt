package org.thechance.service_restaurant.domain.usecase

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.currentDateTime
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import org.thechance.service_restaurant.domain.utils.exceptions.NOT_FOUND
import org.thechance.service_restaurant.domain.utils.exceptions.RESTAURANT_CLOSED

interface IManageOrderUseCase {
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>

    suspend fun addOrder(order: Order): Boolean

    suspend fun updateOrderStatus(orderId: String, state: Order.Status): Order

    suspend fun getOrderById(orderId: String): Order

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int): List<Order>

    suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int
    ): List<Map<Int, Int>> // list of maps (dayOfWeek, count) { dayOfWeek 0 - 6 (Sunday - Saturday) }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int
    ): List<Map<Int, Double>> // list of maps (dayOfWeek, prices) { dayOfWeek 0 - 6 (Sunday - Saturday) }

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

    override suspend fun updateOrderStatus(orderId: String, state: Order.Status): Order {
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
            return doInRangeWithDaysBack(daysBack) { daysOfYearRange, currentDateTime ->
                val groupedOrdersByDayOfYear = optionsGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
                    .filter { it.createdAt.dayOfYear in daysOfYearRange }.groupBy { it.createdAt.dayOfYear }

                daysOfYearRange.map {
                    val count = groupedOrdersByDayOfYear[it]?.size ?: 0
                    mapOf(currentDateTime.toJavaLocalDateTime().withDayOfYear(it).dayOfWeek.value to count)
                }.reversed()
            }
        } ?: throw MultiErrorException(listOf(INVALID_ID))
    }

    override suspend fun getOrdersRevenueByDaysBefore(restaurantId: String, daysBack: Int): List<Map<Int, Double>> {
        basicValidation.isValidId(restaurantId).takeIf { it }?.let {
            return doInRangeWithDaysBack(daysBack) { daysOfYearRange, currentDateTime ->
                val groupedOrdersByDayOfYear = optionsGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
                    .filter { it.createdAt.dayOfYear in daysOfYearRange && it.status == Order.Status.DONE }
                    .groupBy { it.createdAt.dayOfYear }

                daysOfYearRange.map { days ->
                    val prices = groupedOrdersByDayOfYear[days]?.map { it.totalPrice } ?: emptyList()
                    mapOf(currentDateTime.toJavaLocalDateTime().withDayOfYear(days).dayOfWeek.value to prices.sumOf { it })
                }.reversed()
            }
        } ?: throw MultiErrorException(listOf(INVALID_ID))
    }

    private suspend fun isRestaurantOpened(restaurantId: String): Boolean {
        val restaurant = restaurantGateway.getRestaurant(id = restaurantId)
        return restaurant?.isRestaurantOpen() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    private suspend fun<T> doInRangeWithDaysBack(daysBack: Int, function: suspend (IntRange, LocalDateTime) -> T): T {
        val currentDateTime = currentDateTime().toJavaLocalDateTime()
        val currentDayOfYear = currentDateTime.dayOfYear
        val dayOfYearBefore = currentDateTime.minusDays(daysBack.toLong()).dayOfYear
        val daysOfYearRange = dayOfYearBefore..currentDayOfYear
        return function(daysOfYearRange, currentDateTime.toKotlinLocalDateTime())
    }
}