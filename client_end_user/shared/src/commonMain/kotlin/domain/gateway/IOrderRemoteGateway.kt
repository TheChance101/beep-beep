package domain.gateway

import domain.entity.Order
import domain.entity.Trip

interface IOrderRemoteGateway {
    suspend fun getTripHistory(): List<Trip>
    suspend fun getOrderHistoryGateway(): List<Order>
}