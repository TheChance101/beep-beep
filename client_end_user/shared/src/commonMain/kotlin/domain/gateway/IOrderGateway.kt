package domain.gateway

import domain.entity.Cart
import domain.entity.Order
import domain.entity.Trip

interface IOrderGateway {
    suspend fun getTripHistory(): List<Trip> // TODO: need to handle pagination
    suspend fun getOrderHistoryGateway(): List<Order> // TODO: need to handle pagination
    suspend fun getAllCartMeals(): Cart
    suspend fun updateCartMeals(cart: Cart)
}