package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Meal
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway

interface IManageCartUseCase {
    suspend fun addMealToCart(meal: Meal, quantity: Int, userId: String): Boolean
    suspend fun deleteMealFromCart(cartId: String, mealId: String): Boolean
}

class ManageCartUseCase(
    private val restaurantOptions: IRestaurantOptionsGateway,
) : IManageCartUseCase {
    override suspend fun addMealToCart(meal: Meal, quantity: Int, userId: String): Boolean {
        return restaurantOptions.addMealToCart(meal, quantity, userId)
    }

    override suspend fun deleteMealFromCart(cartId: String, mealId: String): Boolean {
        return restaurantOptions.deleteMealFromCart(cartId, mealId)
    }

}