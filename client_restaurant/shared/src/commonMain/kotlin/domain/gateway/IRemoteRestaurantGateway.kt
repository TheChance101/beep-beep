package domain.gateway

import domain.entity.Restaurant


interface IRemoteRestaurantGateway {

    suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant>
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant

}