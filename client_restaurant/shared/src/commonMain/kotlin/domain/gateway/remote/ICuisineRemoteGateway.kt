package domain.gateway.remote

import domain.entity.Cuisine


interface ICuisineRemoteGateway {
    suspend fun getCuisinesByRestaurantId(restaurantId: String): List<Cuisine>?

}