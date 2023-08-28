package org.thechance.api_gateway.endpoints.gateway

import kotlinx.coroutines.flow.Flow
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import java.util.*

interface IRestaurantGateway {
    suspend fun createRequestPermission(
        restaurantName: String, ownerEmail: String, cause: String, locale: Locale
    ): RestaurantRequestPermission

    suspend fun getAllRequestPermission(permissions: List<Int>, locale: Locale): List<RestaurantRequestPermission>

    suspend fun addCuisine(name: String, permissions: List<Int>, locale: Locale): CuisineResource

    suspend fun getCuisines(locale: Locale): CuisineResource

    // region Order
    suspend fun updateOrderStatus(orderId: String, permissions: List<Int>, status: Int, locale: Locale): Order

    suspend fun getOrdersHistory(restaurantId: String, permissions: List<Int>, page: Int, limit: Int, locale: Locale): List<Order>
    // endregion

    suspend fun getRestaurantInfo(locale: Locale, id: String): RestaurantResource

    suspend fun restaurantOrders(permissions: List<Int>, restaurantId: String, locale: Locale) : Flow<Order>

    suspend fun getActiveOrders(permissions: List<Int>, restaurantId: String, locale: Locale): List<Order>
}

