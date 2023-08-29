package domain.gateway.remote

import domain.entity.Cuisine
import domain.entity.Meal


interface IRemoteCuisineGateway {
    suspend fun getCuisinesByRestaurantId(restaurantId: String): List<Cuisine>

}