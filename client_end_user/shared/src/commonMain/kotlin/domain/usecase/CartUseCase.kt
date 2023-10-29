package domain.usecase

import domain.entity.Cart
import domain.gateway.ITransactionsGateway

interface IManageCartUseCase {
    suspend fun getCart(): Cart
    suspend fun orderNow(): Boolean
    suspend fun updateCart(cart: Cart)

}

class ManageCartUseCase(
    private val transactionGateway: ITransactionsGateway
) : IManageCartUseCase {
    override suspend fun getCart(): Cart {
        return transactionGateway.getCart()
    }

    override suspend fun orderNow(): Boolean {
        return transactionGateway.orderNow()
    }

    override suspend fun updateCart(cart: Cart) {
        return transactionGateway.updateCart(cart)
    }


}
