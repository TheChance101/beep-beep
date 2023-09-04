package domain.gateway.remote

import domain.entity.Restaurant


interface IRestaurantRemoteGateway {

    suspend fun getRestaurantsByOwnerId(): List<Restaurant>
    suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean
    suspend fun getRestaurantInfo(restaurantId: String): Restaurant

}