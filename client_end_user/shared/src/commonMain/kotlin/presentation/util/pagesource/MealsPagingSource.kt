package presentation.util.pagesource

import domain.entity.Meal
import domain.entity.PaginationItems
import domain.usecase.IExploreRestaurantUseCase
import presentation.util.pagesource.BasePagingSource
import kotlin.properties.Delegates

class MealsPagingSource(
    private val exploreRestaurant: IExploreRestaurantUseCase
) : BasePagingSource<Meal>() {

    private var cuisineId by Delegates.notNull<String>()

    fun initCuisine(id: String) {
        cuisineId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Meal> {
        return exploreRestaurant.getMealsInCuisine(cuisineId, page, limit)
    }

}
