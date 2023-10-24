package data.gateway.remote.pagesource

import domain.entity.Meal
import domain.entity.PaginationItems
import domain.gateway.IRestaurantGateway
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf
import kotlin.properties.Delegates

class MealsPagingSource(
    private val remoteGateway: IRestaurantGateway,
) : BasePagingSource<Meal>(), KoinComponent {
    private var cuisineId by Delegates.notNull<String>()

    fun initCuisine(id: String) {
        cuisineId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): PaginationItems<Meal> {
        return remoteGateway.getMealsInCuisine(cuisineId, page, limit)
    }

}
