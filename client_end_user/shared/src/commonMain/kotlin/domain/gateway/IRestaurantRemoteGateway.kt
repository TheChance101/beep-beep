package domain.gateway

import domain.entity.Cuisine
import domain.entity.InProgressWrapper
import domain.entity.Restaurant

interface IRestaurantRemoteGateway {
    suspend fun getCuisines(): List<Cuisine>
    suspend fun getInProgress(): InProgressWrapper

    suspend fun getRestaurantDetails(restaurantId: String): Restaurant
}