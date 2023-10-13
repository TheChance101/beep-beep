package domain.usecase

import domain.entity.Cart
import domain.gateway.IOrderGateway

interface IManageCartUseCase {
    suspend fun getCart(): Cart
}

class ManageCartUseCase(
    private val orderGateway: IOrderGateway
) : IManageCartUseCase {
    override suspend fun getCart(): Cart {
        return orderGateway.getAllCartMeals()
    }


}
