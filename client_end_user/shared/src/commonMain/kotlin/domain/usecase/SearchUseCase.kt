package domain.usecase

import domain.entity.Explore
import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantRemoteGateway

interface ISearchUseCase {
    suspend fun searchMealAndRestaurant(query: String): Explore
}


class SearchUseCase(private val restaurantRemoteGateway: IRestaurantRemoteGateway) :
    ISearchUseCase {
    override suspend fun searchMealAndRestaurant(query: String): Explore {
        return restaurantRemoteGateway.search(query.trim())
    }


}
