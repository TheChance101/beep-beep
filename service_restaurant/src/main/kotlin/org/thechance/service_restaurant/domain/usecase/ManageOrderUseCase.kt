package org.thechance.service_restaurant.domain.usecase

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantManagementGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.currentDateTime
import org.thechance.service_restaurant.domain.utils.exceptions.*

interface IManageOrderUseCase {
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>

    suspend fun getActiveOrdersForUser(userId: String): List<Order>

    suspend fun updateOrderStatus(orderId: String): Order
    suspend fun cancelOrder(orderId: String): Order

    suspend fun getOrderById(orderId: String): Order

    suspend fun isOrderExisted(orderId: String): Boolean

    suspend fun getOrdersHistoryForRestaurant(restaurantId: String, page: Int, limit: Int): List<Order>

    suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String, daysBack: Int
    ): List<Map<Int, Int>> // list of maps (dayOfWeek, count) { dayOfWeek 0 - 6 (Sunday - Saturday) }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String, daysBack: Int
    ): List<Map<Int, Double>> // list of maps (dayOfWeek, prices) { dayOfWeek 0 - 6 (Sunday - Saturday) }

    suspend fun getNumberOfOrdersHistoryInRestaurant(restaurantId: String): Long
    suspend fun getNumberOfOrdersHistoryForUser(userId: String): Long
}

class ManageOrderUseCase(
    private val restaurantOperationGateway: IRestaurantManagementGateway,
    private val basicValidation: IValidation,
    private val orderValidationUseCase: IOrderValidationUseCase
) : IManageOrderUseCase {

    override suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order> {
        return restaurantOperationGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
    }

    override suspend fun getActiveOrdersForUser(userId: String): List<Order> {
        return restaurantOperationGateway.getActiveOrdersForUser(userId)
    }

    override suspend fun getOrderById(orderId: String): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantOperationGateway.getOrderById(orderId = orderId)!!
    }

    override suspend fun isOrderExisted(orderId: String): Boolean {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantOperationGateway.isOrderExisted(orderId)
    }

    override suspend fun updateOrderStatus(orderId: String): Order {

        orderValidationUseCase.validateUpdateOrder(orderId = orderId)
        val currentOrderStatus = Order.Status.getOrderStatus(getOrderStatus(orderId))
        val newOrderStatus = when (currentOrderStatus) {
            Order.Status.PENDING -> Order.Status.APPROVED.statusCode
            Order.Status.APPROVED -> Order.Status.IN_COOKING.statusCode
            Order.Status.IN_COOKING -> Order.Status.DONE.statusCode
            Order.Status.DONE -> throw MultiErrorException(listOf(ALREADY_UPDATED))
            else -> throw MultiErrorException(listOf(INVALID_UPDATE_PARAMETER))
        }

        return restaurantOperationGateway.updateOrderStatus(orderId = orderId, status = newOrderStatus)
            ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    override suspend fun cancelOrder(orderId: String): Order {
        val currentOrderStatus = Order.Status.getOrderStatus(getOrderStatus(orderId))
        if (currentOrderStatus != Order.Status.PENDING) {
            throw MultiErrorException(listOf(CANCEL_ERROR))
        }
        return restaurantOperationGateway.cancelOrder(orderId) ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

    private suspend fun getOrderStatus(orderId: String): Int {
        return restaurantOperationGateway.getOrderStatus(orderId)
    }

    override suspend fun getOrdersHistoryForRestaurant(restaurantId: String, page: Int, limit: Int): List<Order> {
        return restaurantOperationGateway.getOrdersHistoryForRestaurant(
            restaurantId = restaurantId,
            page = page,
            limit = limit
        )
    }

    override suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order> {
        if (!basicValidation.isValidId(restaurantId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return restaurantOperationGateway.getActiveOrdersByRestaurantId(restaurantId = restaurantId)
    }

    override suspend fun getOrdersCountByDaysBefore(restaurantId: String, daysBack: Int): List<Map<Int, Int>> {
        basicValidation.isValidId(restaurantId).takeIf { it }?.let {
            return doInRangeWithDaysBack(daysBack) { daysOfYearRange, currentDateTime ->
                val groupedOrdersByDayOfYear =
                    restaurantOperationGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
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
                val groupedOrdersByDayOfYear =
                    restaurantOperationGateway.getOrdersByRestaurantId(restaurantId = restaurantId)
                        .filter { it.createdAt.dayOfYear in daysOfYearRange && it.status == Order.Status.DONE }
                        .groupBy { it.createdAt.dayOfYear }

                daysOfYearRange.map { days ->
                    val prices = groupedOrdersByDayOfYear[days]?.map { it.totalPrice } ?: emptyList()
                    mapOf(
                        currentDateTime.toJavaLocalDateTime()
                            .withDayOfYear(days).dayOfWeek.value to prices.sumOf { it })
                }.reversed()
            }
        } ?: throw MultiErrorException(listOf(INVALID_ID))
    }

    override suspend fun getNumberOfOrdersHistoryInRestaurant(restaurantId: String): Long {
        return restaurantOperationGateway.getNumberOfOrdersHistoryInRestaurant(restaurantId)
    }

    override suspend fun getNumberOfOrdersHistoryForUser(userId: String): Long {
        return restaurantOperationGateway.getNumberOfOrdersHistoryForUser(userId)
    }

    private suspend fun <T> doInRangeWithDaysBack(daysBack: Int, function: suspend (IntRange, LocalDateTime) -> T): T {
        val currentDateTime = currentDateTime().toJavaLocalDateTime()
        val currentDayOfYear = currentDateTime.dayOfYear
        val dayOfYearBefore = currentDateTime.minusDays(daysBack.toLong()).dayOfYear
        val daysOfYearRange = dayOfYearBefore..currentDayOfYear
        return function(daysOfYearRange, currentDateTime.toKotlinLocalDateTime())
    }
}