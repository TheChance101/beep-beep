package domain.gateway

import domain.entity.Restaurant

interface IFavoriteRestaurantGateway {
    suspend fun getFavoriteRestaurants(): List<Restaurant>
    suspend fun addRestaurantToFavorites(restaurantId: String): Boolean
    suspend fun removeRestaurantFromFavorites(restaurantId: String): Boolean
}