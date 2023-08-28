package org.thechance.api_gateway.endpoints.gateway

import kotlinx.coroutines.flow.Flow
import org.thechance.api_gateway.endpoints.model.OrderDto
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import java.util.*

interface IRestaurantGateway {
    suspend fun createRequestPermission(
        restaurantName: String,
        ownerEmail: String,
        cause: String,
        locale: Locale
    ): RestaurantRequestPermission

    suspend fun getAllRequestPermission(permissions: List<Int>, locale: Locale): List<RestaurantRequestPermission>

    suspend fun restaurantOrders(permissions: List<Int>, restaurantId: String, locale: Locale) : Flow<OrderDto>
}

