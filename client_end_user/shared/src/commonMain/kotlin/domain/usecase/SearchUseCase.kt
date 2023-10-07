package domain.usecase

import domain.entity.Explore
import domain.gateway.IRestaurantGateway

interface ISearchUseCase {
    suspend fun searchMealAndRestaurant(query: String): Explore
}


class SearchUseCase(private val restaurantRemoteGateway: IRestaurantGateway) :
    ISearchUseCase {
    override suspend fun searchMealAndRestaurant(query: String): Explore {
        return restaurantRemoteGateway.search(query.trim())
    }


}
