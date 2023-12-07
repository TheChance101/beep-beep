package presentation.util.pagesource

import domain.entity.PaginationItems
import domain.entity.Restaurant
import domain.usecase.IExploreRestaurantUseCase

class RestaurantsPagingSource(
    private val exploreRestaurant: IExploreRestaurantUseCase
) : BasePagingSource<Restaurant>() {

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Restaurant> {
        return exploreRestaurant.getRestaurants(page, limit)
    }

}
