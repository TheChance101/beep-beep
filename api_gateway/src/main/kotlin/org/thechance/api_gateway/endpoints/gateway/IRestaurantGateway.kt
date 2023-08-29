package org.thechance.api_gateway.endpoints.gateway

import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.data.model.CuisineResource
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import java.util.*

interface IRestaurantGateway {
    suspend fun createRequestPermission(
        restaurantName: String, ownerEmail: String, cause: String, locale: Locale
    ): RestaurantRequestPermission

    suspend fun getAllRequestPermission(permissions: List<Int>, locale: Locale): List<RestaurantRequestPermission>

    suspend fun addCuisine(name: String, permissions: List<Int>, locale: Locale): CuisineResource

    suspend fun getCuisines(locale: Locale): List<CuisineResource>

    suspend fun getRestaurants(page: Int, limit: Int, locale: Locale): List<RestaurantResource>

    // region Order
    suspend fun updateOrderStatus(orderId: String, permissions: List<Int>, status: Int, locale: Locale): Order

    suspend fun getOrdersHistory(
        restaurantId: String,
        permissions: List<Int>,
        page: Int,
        limit: Int,
        locale: Locale
    ): List<Order>
    // endregion
  
    suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): RestaurantResource

    suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale, permissions: List<Int>): List<RestaurantResource>

}

