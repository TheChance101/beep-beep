package domain.gateway

import domain.entity.Cart

interface ICartGateway {
    suspend fun getAllCartMeals(): Cart
}
