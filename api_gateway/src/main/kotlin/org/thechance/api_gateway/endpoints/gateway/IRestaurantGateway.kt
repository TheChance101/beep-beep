package org.thechance.api_gateway.endpoints.gateway

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
}

