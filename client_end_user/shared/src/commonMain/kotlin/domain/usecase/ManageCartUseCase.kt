package domain.usecase

import domain.entity.Cart
import domain.gateway.IOrderGateway

interface IManageCartUseCase {
    suspend fun getAllCartMeals(): Cart
    suspend fun checkIfThereIsOrderInCart(): Boolean
}

class ManageCartUseCase(
    private val orderGateway: IOrderGateway
) : IManageCartUseCase {
    override suspend fun getAllCartMeals(): Cart {
        return orderGateway.getAllCartMeals()
    }

    override suspend fun checkIfThereIsOrderInCart(): Boolean {
        val cart = orderGateway.getAllCartMeals()
        return cart.meals.isEmpty()
    }
}