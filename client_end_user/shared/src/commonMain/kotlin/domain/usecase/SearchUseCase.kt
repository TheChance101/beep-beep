package domain.usecase

import domain.entity.Meal
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway

interface ISearchUseCase {
    suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>>
}


class SearchUseCase(private val restaurantRemoteGateway: IRestaurantGateway) :
    ISearchUseCase {
    override suspend fun search(query: String): Pair<List<Restaurant>, List<Meal>> {
        return restaurantRemoteGateway.search(query.trim())
    }


}
