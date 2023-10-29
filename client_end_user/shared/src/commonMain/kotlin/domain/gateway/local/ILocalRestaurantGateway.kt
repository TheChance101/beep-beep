package domain.gateway.local

import domain.entity.Restaurant

interface ILocalRestaurantGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun addRestaurantToFavorites(vararg restaurant: Restaurant): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
    suspend fun clearFavoriteRestaurants(): Boolean
}