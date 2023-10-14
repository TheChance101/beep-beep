package domain.usecase

import domain.entity.Cart
import domain.gateway.ITransactionsGateway

interface IManageCartUseCase {
    suspend fun getCart(): Cart
}

class ManageCartUseCase(
    private val transactionGateway: ITransactionsGateway
) : IManageCartUseCase {
    override suspend fun getCart(): Cart {
        return transactionGateway.getCart()
    }


}
