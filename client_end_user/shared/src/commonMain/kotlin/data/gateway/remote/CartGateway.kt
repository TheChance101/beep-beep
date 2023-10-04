package data.gateway.remote

import domain.entity.Cart
import domain.gateway.ICartGateway

class CartGateway : ICartGateway {
    override suspend fun getAllCartMeals(): Cart {
        TODO("Not yet implemented")
    }
}