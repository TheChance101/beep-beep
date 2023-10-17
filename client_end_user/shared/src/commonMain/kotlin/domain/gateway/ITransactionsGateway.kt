package domain.gateway

import domain.entity.Cart
import domain.entity.FoodOrder
import domain.entity.Trip

interface ITransactionsGateway {
    suspend fun getTripHistory(): List<Trip>
    suspend fun getOrderHistoryGateway(): List<FoodOrder>
    suspend fun getCart(): Cart
    suspend fun getActiveTrips(): List<Trip>
    suspend fun getActiveOrders(): List<FoodOrder>
}
