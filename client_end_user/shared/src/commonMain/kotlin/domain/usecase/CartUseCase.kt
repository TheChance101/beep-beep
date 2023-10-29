package domain.usecase

import domain.entity.Cart
import domain.gateway.ITransactionsGateway

interface IManageCartUseCase {
    suspend fun getCart(): Cart

    suspend fun addMealTCart(mealId: String, restaurantId: String, quantity: Int): Boolean
    suspend fun orderNow(): Boolean
    suspend fun updateCart(cart: Cart)

}

class ManageCartUseCase(
    private val transactionGateway: ITransactionsGateway,
) : IManageCartUseCase {
    override suspend fun getCart(): Cart {
        return transactionGateway.getCart()
    }

    override suspend fun addMealTCart(
        mealId: String, restaurantId: String, quantity: Int,
    ): Boolean {
        val result = transactionGateway.addMealToCart(mealId, restaurantId, quantity)
        return result.meals.isNullOrEmpty()
    }

    override suspend fun orderNow(): Boolean {
        return transactionGateway.orderNow()
    }

    override suspend fun updateCart(cart: Cart) {
        return transactionGateway.updateCart(cart)
    }


}
