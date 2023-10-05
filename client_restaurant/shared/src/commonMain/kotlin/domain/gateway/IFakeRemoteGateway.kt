package domain.gateway

import domain.entity.Category
import domain.entity.Cuisine
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant

interface IFakeRemoteGateway {

    //region login
    suspend fun loginUser(userName: String, password: String): Pair<String, String>

    // endregion

    //region restaurant
    suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant>
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant
    //endregion restaurant

    //region meal
    suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal?
    suspend fun addMeal(meal: Meal): Meal?
    suspend fun updateMeal(meal: Meal): Meal?
    //endregion meal

    //region order
    suspend fun getCurrentOrders(): List<Order>
    suspend fun getOrdersHistory(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: Int): Order
    suspend fun getOrderById(orderId: String): Order?
    //endregion order

    //region category
    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category
    //endregion category

    //region Cuisine
    suspend fun getCuisines(): List<Cuisine>

    suspend fun getCuisinesInMeal(mealId: String): List<Cuisine>

    suspend fun getCuisineByRestaurantId(restaurantId: String): List<Cuisine>

    suspend fun getMealsByCuisineId(id: String): List<Meal>

    //endregion Cuisine

    //region stats
    suspend fun getOrdersRevenueByDaysBefore(
    restaurantId: String, daysBack: Int
     ): List<Map<String, Double>>

    suspend fun getOrdersCountByDaysBefore(
    restaurantId: String, daysBack: Int
        ): List<Map<String,Int>>

    //endregion stats
}
