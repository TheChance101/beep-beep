package org.thechance.api_gateway.endpoints.gateway

import kotlinx.coroutines.flow.Flow
import org.thechance.api_gateway.data.model.CuisineResource
import org.thechance.api_gateway.data.model.restaurant.MealResource
import org.thechance.api_gateway.data.model.restaurant.RestaurantResource
import org.thechance.api_gateway.endpoints.model.Order
import org.thechance.api_gateway.endpoints.model.RestaurantRequestPermission
import java.util.*

interface IRestaurantGateway {
    suspend fun createRequestPermission(
        restaurantName: String,
        ownerEmail: String,
        cause: String,
        locale: Locale
    ): RestaurantRequestPermission

    suspend fun getAllRequestPermission(locale: Locale): List<RestaurantRequestPermission>

    suspend fun addCuisine(name: String, locale: Locale): CuisineResource

    suspend fun getCuisines(locale: Locale): List<CuisineResource>

    suspend fun getRestaurants(page: Int, limit: Int, locale: Locale): List<RestaurantResource>

    // region Order
    suspend fun updateOrderStatus(orderId: String, status: Int, locale: Locale): Order

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int, locale: Locale): List<Order>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int,
        locale: Locale
    ): List<Map<Int, Int>> // list of maps (dayOfWeek, count) { dayOfWeek 0 - 6 (Sunday - Saturday) }
    // endregion

    suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): RestaurantResource

    suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale): List<RestaurantResource>

    suspend fun deleteRestaurant(restaurantId: String, locale: Locale): Boolean

    suspend fun updateRestaurantForAdmin(
        restaurant: RestaurantResource,
        permissions: List<Int>,
        locale: Locale
    ): RestaurantResource

    suspend fun updateRestaurant(
        locale: Locale, restaurant: RestaurantResource,
        permissions: List<Int>
    ): RestaurantResource


    suspend fun addMeal(meal: MealResource, locale: Locale): MealResource

    suspend fun updateMeal(meal: MealResource, locale: Locale): MealResource

    suspend fun restaurantOrders(restaurantId: String, locale: Locale): Flow<Order>

    suspend fun getActiveOrders(restaurantId: String, locale: Locale): List<Order>

    suspend fun getMealsByRestaurantId(restaurantId: String, page: Int, limit: Int, locale: Locale): List<MealResource>

    suspend fun getMealsByCuisineId(cuisineId: String, locale: Locale): List<MealResource>

}