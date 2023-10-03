package org.thechance.service_restaurant.domain.gateway

import org.thechance.service_restaurant.domain.entity.Cart
import org.thechance.service_restaurant.domain.entity.Order

interface IRestaurantManagementGateway {
    //region Order
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>
    suspend fun getActiveOrdersByRestaurantId(restaurantId: String): List<Order>
    suspend fun getOrderById(orderId: String): Order?
    suspend fun addOrder(order: Order): Order
    suspend fun updateOrderStatus(orderId: String, status: Order.Status): Order?
    suspend fun getOrdersHistoryForRestaurant(restaurantId: String, page: Int, limit: Int): List<Order>
    suspend fun getOrdersHistoryForUser(userId: String, page: Int, limit: Int): List<Order>
    suspend fun getNumberOfOrdersHistoryInRestaurant(restaurantId: String): Long
    suspend fun getNumberOfOrdersHistoryForUser(userId: String): Long
    //endregion


    //region Cart
    suspend fun getCart(userId: String): Cart
    suspend fun updateCart(cartId: String, restaurantId: String, mealId: String, quantity: Int): Cart
    suspend fun deleteCart(userId: String)
    suspend fun isCartEmpty(userId: String): Boolean
    //endregion

}