package data.gateway.remote.pagesource

import domain.entity.Meal
import domain.gateway.IRestaurantGateway
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.parameter.parametersOf

class MealsPagingSource(
    private val remoteGateway: IRestaurantGateway,
    private val cuisineId: String
) : BasePagingSource<Meal>() ,KoinComponent{
    override suspend fun fetchData(page: Int, limit: Int): List<Meal> {
        val factory = get<String> { parametersOf(cuisineId)}
        return remoteGateway.getMealsInCuisine(factory, page, limit)
    }

}