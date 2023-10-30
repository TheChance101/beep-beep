package domain.usecase

import domain.entity.Cart
import domain.gateway.ITransactionsGateway
import domain.gateway.local.ILocalConfigurationGateway
import kotlinx.coroutines.flow.Flow

interface IManageCartUseCase {
    suspend fun getCart(): Cart
    suspend fun isCartEmpty(): Flow<Boolean>
    suspend fun addMealTCart(mealId: String, restaurantId: String, quantity: Int): Boolean
    suspend fun orderNow(): Boolean
    suspend fun updateCart(cart: Cart)

}

class ManageCartUseCase(
    private val transactionGateway: ITransactionsGateway,
    private val localGateway: ILocalConfigurationGateway,
) : IManageCartUseCase {
    override suspend fun getCart(): Cart {
        val result = transactionGateway.getCart()
        localGateway.saveCartStatus(isCartEmpty = result.meals.isNullOrEmpty())
        return result
    }

    override suspend fun isCartEmpty(): Flow<Boolean> {
        getCart()
        return localGateway.getCartStatus()
    }

    override suspend fun addMealTCart(
        mealId: String, restaurantId: String, quantity: Int,
    ): Boolean {
        val result = transactionGateway.addMealToCart(mealId, restaurantId, quantity)
        val isAddedSuccessful = !result.meals.isNullOrEmpty()
        localGateway.saveCartStatus(isCartEmpty = !isAddedSuccessful)
        return isAddedSuccessful

    }

    override suspend fun orderNow(): Boolean {
        val result = transactionGateway.orderNow()
        localGateway.saveCartStatus(isCartEmpty = result)
        return result
    }

    override suspend fun updateCart(cart: Cart) {
        return transactionGateway.updateCart(cart)
    }


}
