package domain.usecase

import data.gateway.fake.FakeCartGateway
import domain.entity.Cart

interface IManageCartUseCase {
    suspend fun getAllCartMeals(): Cart
    suspend fun checkIfThereIsOrderInCart(): Boolean
}

class ManageCartUseCase(private val remoteGateway: FakeCartGateway) : IManageCartUseCase {
    override suspend fun getAllCartMeals(): Cart {
        return remoteGateway.getAllCartMeals()
    }

    override suspend fun checkIfThereIsOrderInCart(): Boolean {
       val cart = remoteGateway.getAllCartMeals()
        return cart.meals.isEmpty()
    }
}