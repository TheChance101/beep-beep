package domain.usecase

import domain.entity.Cart
import domain.gateway.IOrderGateway

interface IManageCartUseCase {
    suspend fun getAllCartMeals(): Cart

    suspend fun updateCartMeals(cart: Cart)
    suspend fun checkIfThereIsOrderInCart(): Boolean
    suspend fun orderNow(): Boolean
}

class ManageCartUseCase(
    private val orderGateway: IOrderGateway
) : IManageCartUseCase {
    override suspend fun getAllCartMeals(): Cart {
        return orderGateway.getAllCartMeals()
    }

    override suspend fun updateCartMeals(cart: Cart) {
        return orderGateway.updateCartMeals(cart)
    }

    override suspend fun checkIfThereIsOrderInCart(): Boolean {
        val cart = orderGateway.getAllCartMeals()
        return cart.meals.isEmpty()
    }

    override suspend fun orderNow(): Boolean {
        return orderGateway.orderNow()
    }
}