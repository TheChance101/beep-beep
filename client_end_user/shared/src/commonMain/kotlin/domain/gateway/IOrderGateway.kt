package domain.gateway

import domain.entity.Order
import domain.entity.Trip

interface IOrderGateway {
    suspend fun getTripHistory(): List<Trip>
    suspend fun getOrderHistoryGateway(): List<Order>
}