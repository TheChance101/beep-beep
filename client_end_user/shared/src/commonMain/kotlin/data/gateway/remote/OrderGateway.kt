package data.gateway.remote

import domain.entity.Cart
import domain.entity.Order
import domain.entity.Trip
import domain.gateway.IOrderGateway

class OrderGateway : IOrderGateway {
    override suspend fun getTripHistory(): List<Trip> {
        TODO("Not yet implemented")
    }

    override suspend fun getOrderHistoryGateway(): List<Order> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCartMeals(): Cart {
        TODO("Not yet implemented")
    }
}