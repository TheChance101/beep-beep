package domain.gateway

import domain.entity.Category
import domain.entity.Cousin
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant

interface IRemoteGateWay {

    //region restaurant
    suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant>
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant?
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant?
    //endregion restaurant

    //region meal
    suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal>
    suspend fun getMealById(mealId: String): Meal?
    suspend fun addMeal(meal: Meal): Meal
    suspend fun updateMeal(meal: Meal): Meal
    //endregion meal

    //region order
    suspend fun getCurrentOrders(restaurantId: String): List<Order>
    suspend fun getOrdersHistory(restaurantId: String): List<Order>
    suspend fun updateOrderState(orderId: String, orderState: Int): Order
    suspend fun getOrderById(orderId: String): Order?
    //endregion order

    //region category
    suspend fun getCategoriesByRestaurantId(restaurantId: String): Category
    //endregion category

    // region cousin
    suspend fun getCousins(restaurantId: String): List<Cousin>

    suspend fun getMealsByCousinId(id: String): List<Meal>

    // endregion cousin

}