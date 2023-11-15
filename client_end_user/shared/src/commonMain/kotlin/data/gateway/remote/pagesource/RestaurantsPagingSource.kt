package data.gateway.remote.pagesource

import domain.entity.PaginationItems
import domain.entity.Restaurant
import domain.gateway.IRestaurantGateway

class RestaurantsPagingSource(
    private val remoteGateway: IRestaurantGateway,
) : BasePagingSource<Restaurant>() {

    override suspend fun fetchData(page: Int, limit: Int ): PaginationItems<Restaurant> {
        return remoteGateway.getRestaurants(page, 10)
    }

}
