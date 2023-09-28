package domain.usecase

import data.gateway.fake.FakeRemoteGateway
import domain.entity.Cart

interface IManageCartUseCase {
    suspend fun getAllCartMeals(): Cart
}

class ManageCartUseCase(private val remoteGateway: FakeRemoteGateway) : IManageCartUseCase {
    override suspend fun getAllCartMeals(): Cart {
        return remoteGateway.getAllCartMeals()
    }

}