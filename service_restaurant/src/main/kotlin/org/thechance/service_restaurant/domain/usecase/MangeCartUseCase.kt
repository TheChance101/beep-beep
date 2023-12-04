package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Cart
import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.entity.mapper.toOrder
import org.thechance.service_restaurant.domain.gateway.IRestaurantGateway
import org.thechance.service_restaurant.domain.gateway.IRestaurantManagementGateway
import org.thechance.service_restaurant.domain.usecase.validation.ICartValidationUseCase
import org.thechance.service_restaurant.domain.utils.exceptions.*


interface IMangeCartUseCase {

    suspend fun getUserCart(userId: String): Cart

    suspend fun updateMealInCart(userId: String, restaurantId: String, mealId: String, quantity: Int): Cart

    suspend fun orderCart(userId: String): Order

    suspend fun getOrdersHistoryForUser(userId: String, page: Int, limit: Int): List<Order>
    suspend fun updateCart(userId: String, cart: Cart): Cart
    suspend fun clearCart(userId: String): Boolean

}

class MangeCartUseCase(
    private val restaurantGateway: IRestaurantGateway,
    private val restaurantOperationGateway: IRestaurantManagementGateway,
    private val validations: ICartValidationUseCase
) : IMangeCartUseCase {
    override suspend fun getUserCart(userId: String): Cart {
        return restaurantOperationGateway.getCart(userId)
    }

    override suspend fun updateMealInCart(userId: String, restaurantId: String, mealId: String, quantity: Int): Cart {
        validations.validateUpdateCart(userId, restaurantId, mealId, quantity)
        val cart = restaurantOperationGateway.getCart(userId)
        if (cart.restaurantId != null && cart.restaurantId != restaurantId) {
            throw MultiErrorException(listOf(RESTAURANT_NOT_SAME_IN_CART))
        }
        return restaurantOperationGateway.updateCart(cart.id, restaurantId, mealId, quantity)
    }

    override suspend fun orderCart(userId: String): Order {
        val cart = restaurantOperationGateway.getCart(userId)
        return if (cart.meals.isNullOrEmpty()) {
            throw MultiErrorException(listOf(CART_IS_EMPTY))
        } else if (cart.restaurantId != null && isRestaurantOpened(cart.restaurantId)) {
            restaurantOperationGateway.deleteCart(userId)
            restaurantOperationGateway.addOrder(order = cart.toOrder()) ?: throw MultiErrorException(listOf(NOT_FOUND))
        } else {
            throw MultiErrorException(listOf(RESTAURANT_CLOSED))
        }
    }
    override suspend fun clearCart(userId: String): Boolean {
        val cart = restaurantOperationGateway.getCart(userId)
        return if (cart.meals.isNullOrEmpty()) {
            throw MultiErrorException(listOf(CART_IS_EMPTY))
        } else if (cart.restaurantId != null) {
            restaurantOperationGateway.deleteCart(userId)
            restaurantOperationGateway.isCartEmpty(userId)
        } else {
            throw MultiErrorException(listOf(NOT_FOUND))
        }
    }

    override suspend fun getOrdersHistoryForUser(userId: String, page: Int, limit: Int): List<Order> {
        return restaurantOperationGateway.getOrdersHistoryForUser(userId = userId, page = page, limit = limit)
    }

    override suspend fun updateCart(userId: String, cart: Cart): Cart {
        cart.meals?.forEach { meal ->
            validations.validateUpdateCart(userId, cart.restaurantId ?: "", meal.meadId, meal.quantity)
        }
        return restaurantOperationGateway.updateCart(cart)
    }

    private suspend fun isRestaurantOpened(restaurantId: String): Boolean {
        val restaurant = restaurantGateway.getRestaurant(id = restaurantId)
        return restaurant?.isRestaurantOpen() ?: throw MultiErrorException(listOf(NOT_FOUND))
    }

}