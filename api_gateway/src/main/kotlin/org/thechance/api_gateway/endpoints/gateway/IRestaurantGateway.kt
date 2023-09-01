package org.thechance.api_gateway.endpoints.gateway

import kotlinx.coroutines.flow.Flow
import org.thechance.api_gateway.data.model.BasePaginationResponse
import org.thechance.api_gateway.data.model.Cuisine
import org.thechance.api_gateway.data.model.Order
import org.thechance.api_gateway.data.model.restaurant.Meal
import org.thechance.api_gateway.data.model.restaurant.MealDetails
import org.thechance.api_gateway.data.model.restaurant.Restaurant
import org.thechance.api_gateway.data.model.restaurant.RestaurantRequestPermission
import java.util.*

interface IRestaurantGateway {
    suspend fun createRequestPermission(
        requestedForm: RestaurantRequestPermission, locale: Locale
    ): RestaurantRequestPermission

    suspend fun getAllRequestPermission(locale: Locale): List<RestaurantRequestPermission>

    //region Cuisine
    suspend fun addCuisine(name: String, locale: Locale): Cuisine

    suspend fun getCuisines(locale: Locale): List<Cuisine>
    //endregion


    // region Order
    suspend fun updateOrderStatus(orderId: String, status: Int, locale: Locale): Order

    suspend fun getOrdersHistory(restaurantId: String, page: Int, limit: Int, locale: Locale): List<Order>

    suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int,
        locale: Locale
    ): List<Map<Int, Int>> // list of maps (dayOfWeek, count) { dayOfWeek 0 - 6 (Sunday - Saturday) }

    suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int,
        locale: Locale
    ): List<Map<Int, Double>> // list of maps (dayOfWeek, prices) { dayOfWeek 0 - 6 (Sunday - Saturday) }

    suspend fun restaurantOrders(restaurantId: String, locale: Locale): Flow<Order>

    suspend fun getActiveOrders(restaurantId: String, locale: Locale): List<Order>

    // endregion

    //region Restaurant
    suspend fun getRestaurants(page: Int, limit: Int, locale: Locale): BasePaginationResponse<Restaurant>

    suspend fun getRestaurantInfo(locale: Locale, restaurantId: String): Restaurant

    suspend fun getRestaurantsByOwnerId(ownerId: String, locale: Locale): List<Restaurant>

    suspend fun addRestaurant(restaurant: Restaurant, locale: Locale): Restaurant
    suspend fun updateRestaurant(
        restaurant: Restaurant, isAdmin: Boolean, locale: Locale
    ): Restaurant

    suspend fun deleteRestaurant(restaurantId: String, locale: Locale): Boolean

    //endregion

    //region meal
    suspend fun getMeal(mealId: String, locale: Locale): MealDetails
    suspend fun getMealsByRestaurantId(restaurantId: String, page: Int, limit: Int, locale: Locale): List<Meal>
    suspend fun getMealsByCuisineId(cuisineId: String, locale: Locale): List<Meal>
    suspend fun addMeal(meal: Meal, locale: Locale): Meal
    suspend fun updateMeal(meal: Meal, locale: Locale): Meal
    //endregion
}