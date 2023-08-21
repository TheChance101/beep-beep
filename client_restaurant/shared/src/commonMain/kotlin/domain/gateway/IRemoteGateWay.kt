package domain.gateway

import domain.entity.Category
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant

interface IRemoteGateWay {

    // need to add in restaurant service
    suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant>
    suspend fun getMealById(mealId: String): Meal
    suspend fun getCurrentOrders(restaurantId: String): List<Order>
    suspend fun getOrdersHistory(restaurantId: String): List<Order>
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant
    suspend fun updateOrderState(orderId: String, orderState: Int): Order
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant
    suspend fun updateMeal(meal: Meal): Meal
    suspend fun addMeal(meal: Meal): Meal
    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category
    suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal>

}