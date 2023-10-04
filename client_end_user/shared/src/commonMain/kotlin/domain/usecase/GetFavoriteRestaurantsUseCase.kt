package domain.usecase

import data.gateway.remote.RestaurantGateway
import domain.entity.Restaurant

interface IGetFavoriteRestaurantsUseCase {
    suspend operator fun invoke(): List<Restaurant>
}

class GetFavoriteRestaurantsUseCase(private val restaurantGateway: RestaurantGateway) :
    IGetFavoriteRestaurantsUseCase {
    override suspend operator fun invoke(): List<Restaurant> {
        return restaurantGateway.getFavoriteRestaurants()
    }
}