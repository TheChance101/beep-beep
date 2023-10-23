package data.gateway.remote.pagesource

import domain.entity.Meal
import domain.gateway.IRestaurantGateway
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MealsPagingSource(
    private val remoteGateway: IRestaurantGateway,
) : BasePagingSource<Meal>(), KoinComponent {

    private lateinit var cuisineId: String

    fun initCuisine(id: String) {
        cuisineId = id
    }

    override suspend fun fetchData(page: Int, limit: Int): List<Meal> {
        return remoteGateway.getMealsInCuisine(cuisineId, page, limit)
    }

}
